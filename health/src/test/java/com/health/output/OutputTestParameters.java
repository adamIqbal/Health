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
                                "test(two) = {2.0}")
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
                                "Hello two2.0!")
                },
                new Object[] {
                        "{xyz}+{xyz}",
                        Arrays.asList(
                                "1.0+1.0",
                                "2.0+2.0")
                },
                new Object[] {
                        "test({abc}) = xyz{xyz}",
                        Arrays.asList(
                                "test(one) = xyz1.0",
                                "test(two) = xyz2.0")
                },
        };
    }

    public static Object[] getDelimiter() {
        return new Object[] {
                new Object[] { "a", ", ", "a, a" },
                new Object[] { " ", "\n", " \n " },
                new Object[] { "12-3", "\t", "12-3\t12-3" },
                new Object[] { "", " x ", " x " },
        };
    }
}
