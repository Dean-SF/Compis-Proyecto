/*
 * @(#)Token.java                        2.1 2003/10/07
 *
 * Copyright (C) 1999, 2003 D.A. Watt and D.F. Brown
 * Dept. of Computing Science, University of Glasgow, Glasgow G12 8QQ Scotland
 * and School of Computer and Math Sciences, The Robert Gordon University,
 * St. Andrew Street, Aberdeen AB25 1HG, Scotland.
 * All rights reserved.
 *
 * This software is provided free for educational use only. It may
 * not be used for commercial purposes without the prior written permission
 * of the authors.
 */

package Triangle.SyntacticAnalyzer;


public final class Token extends Object {

  public int kind;
  public String spelling;
  protected SourcePosition position;

  public Token(int kind, String spelling, SourcePosition position) {

    if (kind == Token.IDENTIFIER) {
      int currentKind = firstReservedWord;
      boolean searching = true;

      while (searching) {
        int comparison = tokenTable[currentKind].compareTo(spelling);
        if (comparison == 0) {
          this.kind = currentKind;
          searching = false;
        } else if (comparison > 0 || currentKind == lastReservedWord) {
          this.kind = Token.IDENTIFIER;
          searching = false;
        } else {
          currentKind ++;
        }
      }
    } else
      this.kind = kind;

    this.spelling = spelling;
    this.position = position;

  }

  public static String spell (int kind) {
    return tokenTable[kind];
  }

  public String toString() {
    return "Kind=" + kind + ", spelling=" + spelling +
      ", position=" + position;
  }

  // Token classes...

  public static final int

    // literals, identifiers, operators...
    INTLITERAL	= 0,
    CHARLITERAL	= 1,
    IDENTIFIER	= 2,
    OPERATOR	= 3,

    // reserved words - must be in alphabetical order...
    ARRAY		= 4,
    CONST		= 5,
    DO			= 6,
    ELSE		= 7,
    END			= 8,
    FOR     = 9,
    FROM    = 10,
    FUNC		= 11,
    IF			= 12,
    IN			= 13,
    LET			= 14,
    OF			= 15,
    PACKAGE = 16,
    PRIVATE = 17,
    PROC		= 18,
    REC     = 19,
    RECORD	= 20,
    REPEAT  = 21,
    SELECT  = 22,
    SKIP    = 23,
    THEN		= 24,
    TIMES   = 25,
    TYPE		= 26,
    UNTIL   = 27,
    VAR			= 28,
    WHEN    = 29,
    WHILE		= 30,

    // punctuation...
    DOT			= 31,
    DENOTE  = 32,
    OR      = 33,
    COLON		= 34,
    SEMICOLON	= 35,
    COMMA		= 36,
    BECOMES	= 37,
    IS			= 38,

    // brackets...
    LPAREN		= 39,
    RPAREN		= 40,
    LBRACKET	= 41,
    RBRACKET	= 42,
    LCURLY		= 43,
    RCURLY		= 44,

    // special tokens...
    EOT			= 45,
    ERROR		= 46;

  private static String[] tokenTable = new String[] {
    "<int>",
    "<char>",
    "<identifier>",
    "<operator>",
    "array",
    "const",
    "do",
    "else",
    "end",
    "for",
    "from",
    "func",
    "if",
    "in",
    "let",
    "of",
    "package",
    "private",
    "proc",
    "rec",
    "record",
    "repeat",
    "select",
    "skip",
    "then",
    "times",
    "type",
    "until",
    "var",
    "when",
    "while",
    ".",
    "$",
    "|",
    ":",
    ";",
    ",",
    ":=",
    "~",
    "(",
    ")",
    "[",
    "]",
    "{",
    "}",
    "",
    "<error>"
  };

  private final static int	firstReservedWord = Token.ARRAY,
  				lastReservedWord  = Token.WHILE;

}


