/* TypeParser.java */
/* Generated By:JavaCC: Do not edit this line. TypeParser.java */
package org.starcoin.bcs.sab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TypeParser implements TypeParserConstants {
    static private final LookaheadSuccess jj_ls = new LookaheadSuccess();
    static private int[] jj_la1_0;

    static {
        jj_la1_init_0();
    }

    final private int[] jj_la1 = new int[0];
    final private JJCalls[] jj_2_rtns = new JJCalls[16];
    /**
     * Generated Token Manager.
     */
    public TypeParserTokenManager token_source;
    /**
     * Current token.
     */
    public Token token;
    /**
     * Next token.
     */
    public Token jj_nt;
    SimpleCharStream jj_input_stream;
    private int jj_ntk;
    private Token jj_scanpos, jj_lastpos;
    private int jj_la;
    private int jj_gen;
    private boolean jj_rescan = false;
    private int jj_gc = 0;
    private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
    private int[] jj_expentry;
    private int jj_kind = -1;
    private int[] jj_lasttokens = new int[100];
    private int jj_endpos;
    private boolean trace_enabled;

    public TypeParser(String input) {
        this(new java.io.ByteArrayInputStream(input.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
    }

    /**
     * Constructor with InputStream.
     */
    public TypeParser(java.io.InputStream stream) {
        this(stream, null);
    }

    /**
     * Constructor with InputStream and supplied encoding
     */
    public TypeParser(java.io.InputStream stream, String encoding) {
        try {
            jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1);
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        token_source = new TypeParserTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    /**
     * Constructor.
     */
    public TypeParser(java.io.Reader stream) {
        jj_input_stream = new SimpleCharStream(stream, 1, 1);
        token_source = new TypeParserTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    /**
     * Constructor with generated Token Manager.
     */
    public TypeParser(TypeParserTokenManager tm) {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    public static void main(String[] args) throws ParseException, TokenMgrError {
        TypeParser parser = new TypeParser(System.in);
        parser.typeAndPath();
    }

    private static void jj_la1_init_0() {
        jj_la1_0 = new int[]{};
    }

    final public Pair<BCSTraverser, List<Integer>> typeAndPath() throws ParseException {
        BCSTraverser t;
        List<Integer> p;
        t = type();
        p = path();
        {
            if ("" != null) return new Pair<>(t, p);
        }
        throw new Error("Missing return statement in function");
    }

    final public List<Integer> path() throws ParseException {
        Token t;
        List<Integer> indexes = new ArrayList<>();
        label_1:
        while (true) {
            if (jj_2_1(2)) {
            } else {
                break label_1;
            }
            jj_consume_token(LBRACKET);
            t = jj_consume_token(UINTEGER);
            indexes.add(Integer.parseInt(t.image));
            jj_consume_token(RBRACKET);
        }
        {
            if ("" != null) return indexes;
        }
        throw new Error("Missing return statement in function");
    }

    final public BCSTraverser type() throws ParseException {
        BCSTraverser t;
        if (jj_2_2(2)) {
            jj_consume_token(BYTES);
            {
                if ("" != null) return BytesTraverser.INSTANCE;
            }
        } else if (jj_2_3(2)) {
            jj_consume_token(STRING);
            {
                if ("" != null) return StringTraverser.INSTANCE;
            }
        } else if (jj_2_4(2)) {
            jj_consume_token(U128);
            {
                if ("" != null) return U128Traverser.INSTANCE;
            }
        } else if (jj_2_5(2)) {
            jj_consume_token(U64);
            {
                if ("" != null) return U64Traverser.INSTANCE;
            }
        } else if (jj_2_6(2)) {
            jj_consume_token(U8);
            {
                if ("" != null) return U8Traverser.INSTANCE;
            }
        } else if (jj_2_7(2)) {
            jj_consume_token(BOOL);
            {
                if ("" != null) return BoolTraverser.INSTANCE;
            }
        } else if (jj_2_8(2)) {
            jj_consume_token(ADDRESS);
            {
                if ("" != null) return AccountAddressTraverser.INSTANCE;
            }
        } else if (jj_2_9(2)) {
            t = struct();
            {
                if ("" != null) return t;
            }
        } else if (jj_2_10(2)) {
            t = _enum();
            {
                if ("" != null) return t;
            }
        } else if (jj_2_11(2)) {
            t = vector();
            {
                if ("" != null) return t;
            }
        } else if (jj_2_12(2)) {
            t = optional();
            {
                if ("" != null) return t;
            }
        } else if (jj_2_13(2)) {
            jj_consume_token(UNDERSCORE);
            {
                if ("" != null) return NoOperationTraverser.INSTANCE;
            }
        } else {
            jj_consume_token(-1);
            throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    final public BCSTraverser struct() throws ParseException {
        List<BCSTraverser> tl;
        if (jj_2_14(2)) {
            jj_consume_token(LBRACE);
            jj_consume_token(RBRACE);
            {
                if ("" != null) return new StructTraverser(Collections.emptyList());
            }
        } else if (jj_2_15(2)) {
            jj_consume_token(LBRACE);
            tl = notEmptyTypeList();
            jj_consume_token(RBRACE);
            {
                if ("" != null) return new StructTraverser(tl);
            }
        } else {
            jj_consume_token(-1);
            throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    final public BCSTraverser _enum() throws ParseException {
        List<BCSTraverser> tl;
        jj_consume_token(ENUM);
        jj_consume_token(LBRACE);
        tl = notEmptyTypeList();
        jj_consume_token(RBRACE);
        {
            if ("" != null) return new EnumTraverser(tl);
        }
        throw new Error("Missing return statement in function");
    }

    final public List<BCSTraverser> notEmptyTypeList() throws ParseException {
        List<BCSTraverser> tl = new ArrayList<>();
        BCSTraverser t;
        t = type();
        tl.add(t);
        label_2:
        while (true) {
            if (jj_2_16(2)) {
            } else {
                break label_2;
            }
            jj_consume_token(COMMA);
            t = type();
            tl.add(t);
        }
        {
            if ("" != null) return tl;
        }
        throw new Error("Missing return statement in function");
    }

    final public BCSTraverser vector() throws ParseException {
        BCSTraverser t;
        jj_consume_token(VECTOR);
        jj_consume_token(LT);
        t = type();
        jj_consume_token(GT);
        if (Objects.equals(t, U8Traverser.INSTANCE)) {
            {
                if ("" != null) return VectorU8Traverser.INSTANCE;
            }
        } else {
            {
                if ("" != null) return new VectorTraverser(t);
            }
        }
        throw new Error("Missing return statement in function");
    }

    final public BCSTraverser optional() throws ParseException {
        BCSTraverser t;
        jj_consume_token(OPTIONAL);
        jj_consume_token(LT);
        t = type();
        jj_consume_token(GT);
        {
            if ("" != null) return new OptionalTraverser(t);
        }
        throw new Error("Missing return statement in function");
    }

    private boolean jj_2_1(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return (!jj_3_1());
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(0, xla);
        }
    }

    private boolean jj_2_2(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return (!jj_3_2());
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(1, xla);
        }
    }

    private boolean jj_2_3(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return (!jj_3_3());
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(2, xla);
        }
    }

    private boolean jj_2_4(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return (!jj_3_4());
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(3, xla);
        }
    }

    private boolean jj_2_5(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return (!jj_3_5());
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(4, xla);
        }
    }

    private boolean jj_2_6(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return (!jj_3_6());
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(5, xla);
        }
    }

    private boolean jj_2_7(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return (!jj_3_7());
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(6, xla);
        }
    }

    private boolean jj_2_8(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return (!jj_3_8());
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(7, xla);
        }
    }

    private boolean jj_2_9(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return (!jj_3_9());
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(8, xla);
        }
    }

    private boolean jj_2_10(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return (!jj_3_10());
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(9, xla);
        }
    }

    private boolean jj_2_11(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return (!jj_3_11());
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(10, xla);
        }
    }

    private boolean jj_2_12(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return (!jj_3_12());
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(11, xla);
        }
    }

    private boolean jj_2_13(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return (!jj_3_13());
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(12, xla);
        }
    }

    private boolean jj_2_14(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return (!jj_3_14());
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(13, xla);
        }
    }

    private boolean jj_2_15(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return (!jj_3_15());
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(14, xla);
        }
    }

    private boolean jj_2_16(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return (!jj_3_16());
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(15, xla);
        }
    }

    private boolean jj_3_1() {
        if (jj_scan_token(LBRACKET)) return true;
        return jj_scan_token(UINTEGER);
    }

    private boolean jj_3R__enum_126_5_4() {
        if (jj_scan_token(ENUM)) return true;
        return jj_scan_token(LBRACE);
    }

    private boolean jj_3_15() {
        if (jj_scan_token(LBRACE)) return true;
        return jj_3R_notEmptyTypeList_136_5_7();
    }

    private boolean jj_3_14() {
        if (jj_scan_token(LBRACE)) return true;
        return jj_scan_token(RBRACE);
    }

    private boolean jj_3R_struct_117_9_3() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_14()) {
            jj_scanpos = xsp;
            return jj_3_15();
        }
        return false;
    }

    private boolean jj_3_16() {
        if (jj_scan_token(COMMA)) return true;
        return jj_3R_type_98_5_8();
    }

    private boolean jj_3R_optional_160_5_6() {
        if (jj_scan_token(OPTIONAL)) return true;
        return jj_scan_token(LT);
    }

    private boolean jj_3_13() {
        return jj_scan_token(UNDERSCORE);
    }

    private boolean jj_3_12() {
        return jj_3R_optional_160_5_6();
    }

    private boolean jj_3_11() {
        return jj_3R_vector_145_5_5();
    }

    private boolean jj_3_10() {
        return jj_3R__enum_126_5_4();
    }

    private boolean jj_3_9() {
        return jj_3R_struct_117_9_3();
    }

    private boolean jj_3_8() {
        return jj_scan_token(ADDRESS);
    }

    private boolean jj_3_7() {
        return jj_scan_token(BOOL);
    }

    private boolean jj_3_6() {
        return jj_scan_token(U8);
    }

    private boolean jj_3_5() {
        return jj_scan_token(U64);
    }

    private boolean jj_3_4() {
        return jj_scan_token(U128);
    }

    private boolean jj_3_3() {
        return jj_scan_token(STRING);
    }

    private boolean jj_3R_type_98_5_8() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_2()) {
            jj_scanpos = xsp;
            if (jj_3_3()) {
                jj_scanpos = xsp;
                if (jj_3_4()) {
                    jj_scanpos = xsp;
                    if (jj_3_5()) {
                        jj_scanpos = xsp;
                        if (jj_3_6()) {
                            jj_scanpos = xsp;
                            if (jj_3_7()) {
                                jj_scanpos = xsp;
                                if (jj_3_8()) {
                                    jj_scanpos = xsp;
                                    if (jj_3_9()) {
                                        jj_scanpos = xsp;
                                        if (jj_3_10()) {
                                            jj_scanpos = xsp;
                                            if (jj_3_11()) {
                                                jj_scanpos = xsp;
                                                if (jj_3_12()) {
                                                    jj_scanpos = xsp;
                                                    return jj_3_13();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3R_vector_145_5_5() {
        if (jj_scan_token(VECTOR)) return true;
        return jj_scan_token(LT);
    }

    private boolean jj_3_2() {
        return jj_scan_token(BYTES);
    }

    private boolean jj_3R_notEmptyTypeList_136_5_7() {
        return jj_3R_type_98_5_8();
    }

    /**
     * Reinitialise.
     */
    public void ReInit(java.io.InputStream stream) {
        ReInit(stream, null);
    }

    /**
     * Reinitialise.
     */
    public void ReInit(java.io.InputStream stream, String encoding) {
        try {
            jj_input_stream.ReInit(stream, encoding, 1, 1);
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 0; i++) jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    /**
     * Reinitialise.
     */
    public void ReInit(java.io.Reader stream) {
        if (jj_input_stream == null) {
            jj_input_stream = new SimpleCharStream(stream, 1, 1);
        } else {
            jj_input_stream.ReInit(stream, 1, 1);
        }
        if (token_source == null) {
            token_source = new TypeParserTokenManager(jj_input_stream);
        }

        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    /**
     * Reinitialise.
     */
    public void ReInit(TypeParserTokenManager tm) {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    private Token jj_consume_token(int kind) throws ParseException {
        Token oldToken;
        if ((oldToken = token).next != null) token = token.next;
        else token = token.next = token_source.getNextToken();
        jj_ntk = -1;
        if (token.kind == kind) {
            jj_gen++;
            if (++jj_gc > 100) {
                jj_gc = 0;
                for (int i = 0; i < jj_2_rtns.length; i++) {
                    JJCalls c = jj_2_rtns[i];
                    while (c != null) {
                        if (c.gen < jj_gen) c.first = null;
                        c = c.next;
                    }
                }
            }
            return token;
        }
        token = oldToken;
        jj_kind = kind;
        throw generateParseException();
    }

    private boolean jj_scan_token(int kind) {
        if (jj_scanpos == jj_lastpos) {
            jj_la--;
            if (jj_scanpos.next == null) {
                jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
            } else {
                jj_lastpos = jj_scanpos = jj_scanpos.next;
            }
        } else {
            jj_scanpos = jj_scanpos.next;
        }
        if (jj_rescan) {
            int i = 0;
            Token tok = token;
            while (tok != null && tok != jj_scanpos) {
                i++;
                tok = tok.next;
            }
            if (tok != null) jj_add_error_token(kind, i);
        }
        if (jj_scanpos.kind != kind) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
        return false;
    }

    /**
     * Get the next Token.
     */
    final public Token getNextToken() {
        if (token.next != null) token = token.next;
        else token = token.next = token_source.getNextToken();
        jj_ntk = -1;
        jj_gen++;
        return token;
    }

    /**
     * Get the specific Token.
     */
    final public Token getToken(int index) {
        Token t = token;
        for (int i = 0; i < index; i++) {
            if (t.next != null) t = t.next;
            else t = t.next = token_source.getNextToken();
        }
        return t;
    }

    private int jj_ntk_f() {
        if ((jj_nt = token.next) == null)
            return (jj_ntk = (token.next = token_source.getNextToken()).kind);
        else
            return (jj_ntk = jj_nt.kind);
    }

    private void jj_add_error_token(int kind, int pos) {
        if (pos >= 100) {
            return;
        }

        if (pos == jj_endpos + 1) {
            jj_lasttokens[jj_endpos++] = kind;
        } else if (jj_endpos != 0) {
            jj_expentry = new int[jj_endpos];

            for (int i = 0; i < jj_endpos; i++) {
                jj_expentry[i] = jj_lasttokens[i];
            }

            for (int[] oldentry : jj_expentries) {
                if (oldentry.length == jj_expentry.length) {
                    boolean isMatched = true;

                    for (int i = 0; i < jj_expentry.length; i++) {
                        if (oldentry[i] != jj_expentry[i]) {
                            isMatched = false;
                            break;
                        }

                    }
                    if (isMatched) {
                        jj_expentries.add(jj_expentry);
                        break;
                    }
                }
            }

            if (pos != 0) {
                jj_lasttokens[(jj_endpos = pos) - 1] = kind;
            }
        }
    }

    /**
     * Generate ParseException.
     */
    public ParseException generateParseException() {
        jj_expentries.clear();
        boolean[] la1tokens = new boolean[29];
        if (jj_kind >= 0) {
            la1tokens[jj_kind] = true;
            jj_kind = -1;
        }
        for (int i = 0; i < 0; i++) {
            if (jj_la1[i] == jj_gen) {
                for (int j = 0; j < 32; j++) {
                    if ((jj_la1_0[i] & (1 << j)) != 0) {
                        la1tokens[j] = true;
                    }
                }
            }
        }
        for (int i = 0; i < 29; i++) {
            if (la1tokens[i]) {
                jj_expentry = new int[1];
                jj_expentry[0] = i;
                jj_expentries.add(jj_expentry);
            }
        }
        jj_endpos = 0;
        jj_rescan_token();
        jj_add_error_token(0, 0);
        int[][] exptokseq = new int[jj_expentries.size()][];
        for (int i = 0; i < jj_expentries.size(); i++) {
            exptokseq[i] = jj_expentries.get(i);
        }
        return new ParseException(token, exptokseq, tokenImage);
    }

    /**
     * Trace enabled.
     */
    final public boolean trace_enabled() {
        return trace_enabled;
    }

    /**
     * Enable tracing.
     */
    final public void enable_tracing() {
    }

    /**
     * Disable tracing.
     */
    final public void disable_tracing() {
    }

    private void jj_rescan_token() {
        jj_rescan = true;
        for (int i = 0; i < 16; i++) {
            try {
                JJCalls p = jj_2_rtns[i];

                do {
                    if (p.gen > jj_gen) {
                        jj_la = p.arg;
                        jj_lastpos = jj_scanpos = p.first;
                        switch (i) {
                            case 0:
                                jj_3_1();
                                break;
                            case 1:
                                jj_3_2();
                                break;
                            case 2:
                                jj_3_3();
                                break;
                            case 3:
                                jj_3_4();
                                break;
                            case 4:
                                jj_3_5();
                                break;
                            case 5:
                                jj_3_6();
                                break;
                            case 6:
                                jj_3_7();
                                break;
                            case 7:
                                jj_3_8();
                                break;
                            case 8:
                                jj_3_9();
                                break;
                            case 9:
                                jj_3_10();
                                break;
                            case 10:
                                jj_3_11();
                                break;
                            case 11:
                                jj_3_12();
                                break;
                            case 12:
                                jj_3_13();
                                break;
                            case 13:
                                jj_3_14();
                                break;
                            case 14:
                                jj_3_15();
                                break;
                            case 15:
                                jj_3_16();
                                break;
                        }
                    }
                    p = p.next;
                } while (p != null);

            } catch (LookaheadSuccess ls) {
            }
        }
        jj_rescan = false;
    }

    private void jj_save(int index, int xla) {
        JJCalls p = jj_2_rtns[index];
        while (p.gen > jj_gen) {
            if (p.next == null) {
                p = p.next = new JJCalls();
                break;
            }
            p = p.next;
        }

        p.gen = jj_gen + xla - jj_la;
        p.first = token;
        p.arg = xla;
    }

    public static class Pair<T1, T2> {
        private T1 item1;
        private T2 item2;

        public Pair(T1 item1, T2 item2) {
            this.item1 = item1;
            this.item2 = item2;
        }

        public T1 getItem1() {
            return item1;
        }

        public T2 getItem2() {
            return item2;
        }
    }

    @SuppressWarnings("serial")
    static private final class LookaheadSuccess extends java.lang.Error {
        @Override
        public Throwable fillInStackTrace() {
            return this;
        }
    }

    static final class JJCalls {
        int gen;
        Token first;
        int arg;
        JJCalls next;
    }

}
