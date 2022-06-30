package org.starcoin.dao.api.hateoas.listener;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import org.atteo.evo.inflector.English;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import org.starcoin.dao.api.hateoas.event.PaginatedResultsRetrievedEvent;
import org.starcoin.dao.api.utils.LinkUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.StringJoiner;

@SuppressWarnings({"rawtypes"})
@Component
class PaginatedResultsRetrievedDiscoverabilityListener implements ApplicationListener<PaginatedResultsRetrievedEvent> {

    private static final String PAGE = "page";

    public PaginatedResultsRetrievedDiscoverabilityListener() {
        super();
    }

    // API

    private static String toCamelCase(String s) {
        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }

    @Override
    public final void onApplicationEvent(final PaginatedResultsRetrievedEvent ev) {
        Preconditions.checkNotNull(ev);

        addLinkHeaderOnPagedResourceRetrieval(ev.getUriBuilder(), ev.getResponse(), ev.getClazz(), ev.getPage(),
                ev.getTotalPages(), ev.getPageSize());
    }

    final void addLinkHeaderOnPagedResourceRetrieval(final UriComponentsBuilder uriBuilder,
                                                     final HttpServletResponse response, final Class clazz, final int page, final int totalPages,
                                                     final int pageSize) {
        plural(uriBuilder, clazz);

        final StringJoiner linkHeader = new StringJoiner(", ");
        if (hasNextPage(page, totalPages)) {
            final String uriForNextPage = constructNextPageUri(uriBuilder, page, pageSize);
            linkHeader.add(LinkUtil.createLinkHeader(uriForNextPage, LinkUtil.REL_NEXT));
        }
        if (hasPreviousPage(page)) {
            final String uriForPrevPage = constructPrevPageUri(uriBuilder, page, pageSize);
            linkHeader.add(LinkUtil.createLinkHeader(uriForPrevPage, LinkUtil.REL_PREV));
        }
        if (hasFirstPage(page)) {
            final String uriForFirstPage = constructFirstPageUri(uriBuilder, pageSize);
            linkHeader.add(LinkUtil.createLinkHeader(uriForFirstPage, LinkUtil.REL_FIRST));
        }
        if (hasLastPage(page, totalPages)) {
            final String uriForLastPage = constructLastPageUri(uriBuilder, totalPages, pageSize);
            linkHeader.add(LinkUtil.createLinkHeader(uriForLastPage, LinkUtil.REL_LAST));
        }

        if (linkHeader.length() > 0) {
            response.addHeader(HttpHeaders.LINK, linkHeader.toString());
        }
    }

    final String constructNextPageUri(final UriComponentsBuilder uriBuilder, final int page, final int size) {
        return uriBuilder.replaceQueryParam(PAGE, page + 1)
                .replaceQueryParam("size", size)
                .build()
                .encode()
                .toUriString();
    }

    final String constructPrevPageUri(final UriComponentsBuilder uriBuilder, final int page, final int size) {
        return uriBuilder.replaceQueryParam(PAGE, page - 1)
                .replaceQueryParam("size", size)
                .build()
                .encode()
                .toUriString();
    }

    final String constructFirstPageUri(final UriComponentsBuilder uriBuilder, final int size) {
        return uriBuilder.replaceQueryParam(PAGE, 0)
                .replaceQueryParam("size", size)
                .build()
                .encode()
                .toUriString();
    }

    final String constructLastPageUri(final UriComponentsBuilder uriBuilder, final int totalPages, final int size) {
        return uriBuilder.replaceQueryParam(PAGE, totalPages)
                .replaceQueryParam("size", size)
                .build()
                .encode()
                .toUriString();
    }

    final boolean hasNextPage(final int page, final int totalPages) {
        return page < (totalPages - 1);
    }

    final boolean hasPreviousPage(final int page) {
        return page > 0;
    }

    final boolean hasFirstPage(final int page) {
        return hasPreviousPage(page);
    }

    // template

    final boolean hasLastPage(final int page, final int totalPages) {
        return (totalPages > 1) && hasNextPage(page, totalPages);
    }

    protected void plural(final UriComponentsBuilder uriBuilder, final Class clazz) {
        // - the URI is transformed into plural (added `s`) in a hardcoded way
        //final String resourceName = clazz.getSimpleName().toLowerCase() + "s";
        //
        final String resourceName = English.plural(toCamelCase(clazz.getSimpleName()));
        uriBuilder.path("/v1").path("/" + resourceName);
    }
}
