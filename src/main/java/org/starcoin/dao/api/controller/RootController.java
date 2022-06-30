package org.starcoin.dao.api.controller;

import com.google.common.net.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriTemplate;
import org.starcoin.dao.api.utils.LinkUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.StringJoiner;


@Controller
public class RootController {

    private static String getLinkToCollection(String rootUri, String collectionName) {
        String uriTemplate = "{rootUri}v1/{resource}";
        final URI accountVotesUri = new UriTemplate(uriTemplate).expand(rootUri, collectionName);
        final String linkToAccountVotes = LinkUtil.createLinkHeader(accountVotesUri.toASCIIString(), collectionName);
        return linkToAccountVotes;
    }

    // API discover
    @GetMapping("/")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void adminRoot(final HttpServletRequest request, final HttpServletResponse response) {
        final String rootUri = request.getRequestURL()
                .toString();
        final StringJoiner linkHeader = new StringJoiner(", ");
        linkHeader.add(getLinkToCollection(rootUri, "accountVotes"));
        linkHeader.add(getLinkToCollection(rootUri, "proposals"));
        response.addHeader(HttpHeaders.LINK, linkHeader.toString());
    }

}
