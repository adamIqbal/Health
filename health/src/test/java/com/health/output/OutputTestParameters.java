package com.health.output;

import java.util.Arrays;

/**
 * Provides parameters for OutputTest.
 */
public class OutputTestParameters {
    public static Object[] getFormatWithUnmatchedOpeningBrace() {
        return new Object[] {
                "{",
                "{{{",
                "{ {",
                "{abc",
                "{ abc",
                "abc{",
                "abc {",
                "{abc}{",
                "{abc} {"
        };
    }

    public static Object[] getFormatWithUnmatchedClosingBrace() {
        return new Object[] {
                "}",
                "}}}",
                "} }",
                "}abc",
                "} abc",
                "abc}",
                "abc }",
                "{{abc}",
                "{abc}}",
                "{abc} }"
        };
    }

    public static Object[] getFormatWithUnmatchedColumn() {
        return new Object[] {
                "{}",
                "{acb}",
                "{abc}{}",
                "{abc}{xy}",
                "abc {acb}",
                "{{{acb}",
                "{abc }"
        };
    }

    public static Object[] getFormatWithEscapeBrace() {
        return new Object[] {
                new Object[] {
                        "{{",
                        Arrays.asList(
                                "{",
                                "{")
                },
                new Object[] {
                        "test}}",
                        Arrays.asList(
                                "test}",
                                "test}")
                },
                new Object[] {
                        "{{{abc}",
                        Arrays.asList(
                                "{one",
                                "{two")
                },
                new Object[] {
                        "test({abc}) = {{{xyz}}}",
                        Arrays.asList(
                                "test(one) = {1.0}",
                                "test(two) = {}")
                },
        };
    }

    public static Object[] getFormat() {
        return new Object[] {
                new Object[] {
                        "",
                        Arrays.asList(
                                "",
                                "")
                },
                new Object[] {
                        "abc",
                        Arrays.asList(
                                "abc",
                                "abc")
                },
                new Object[] {
                        "{abc}",
                        Arrays.asList(
                                "one",
                                "two")
                },
                new Object[] {
                        "{{{abc}}}",
                        Arrays.asList(
                                "{one}",
                                "{two}")
                },
                new Object[] {
                        "Hello {abc}{xyz}!",
                        Arrays.asList(
                                "Hello one1.0!",
                                "Hello two!")
                },
                new Object[] {
                        "{xyz}+{xyz}",
                        Arrays.asList(
                                "1.0+1.0",
                                "+")
                },
                new Object[] {
                        "test({abc}) = xyz{xyz}",
                        Arrays.asList(
                                "test(one) = xyz1.0",
                                "test(two) = xyz")
                },
        };
    }

    public static Object[] getFormatForWriteTable() {
        return new Object[] {
                new Object[] { "", "\n" },
                new Object[] { "%", "%\n%" },
                new Object[] { "format", "format\nformat" },
                new Object[] { "{abc}", "one\ntwo" },
                new Object[] { "{abc}={xyz}", "one=1.0\ntwo=" },
                new Object[] { "{{ test {abc}={{{xyz}}} }}",
                        "{ test one={1.0} }\n{ test two={} }" },
        };
    }

    public static Object[] getFormatAndDelimiterForWriteTable() {
        return new Object[] {
                new Object[] { "", " ", " " },
                new Object[] { "%", "x", "%x%" },
                new Object[] { "format", "+", "format+format" },
                new Object[] { "{abc}", "newline", "onenewlinetwo" },
                new Object[] { "{abc}={xyz}", ";", "one=1.0;two=" },
                new Object[] { "{{ test {abc}={{{xyz}}} }}", "\\n",
                        "{ test one={1.0} }\\n{ test two={} }" },
        };
    }
}
