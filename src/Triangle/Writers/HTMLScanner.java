/*
 * @(#)Scanner.java                        2.1 2003/10/07
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

package Triangle.Writers;

import java.io.IOException;

import Triangle.SyntacticAnalyzer.SourceFile;
import Triangle.SyntacticAnalyzer.SourcePosition;
import Triangle.SyntacticAnalyzer.Token;


// Hecho por Deyan copia del Scanner original adaptada para crear HTML
public final class HTMLScanner {

  private WriterHTML writerHTML;
  private SourceFile sourceFile;
  private Token currentToken;

  private char currentChar;
  private StringBuffer currentSpelling;

  private boolean isLetter(char c) {
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
  }

  private boolean isDigit(char c) {
    return (c >= '0' && c <= '9');
  }

// isOperator returns true iff the given character is an operator character.

  private boolean isOperator(char c) {
    return (c == '+' || c == '-' || c == '*' || c == '/' ||
	    c == '=' || c == '<' || c == '>' || c == '\\' ||
	    c == '&' || c == '@' || c == '%' || c == '^' ||
	    c == '?');
  }


///////////////////////////////////////////////////////////////////////////////

  public HTMLScanner(String sourceName) {
    this.writerHTML = new WriterHTML(sourceName);
    sourceFile = new SourceFile(sourceName);;
    currentChar = sourceFile.getSource();

  }

  // takeIt appends the current character to the current token, and gets
  // the next character from the source program.
  private void takeIt() {
    currentSpelling.append(currentChar);
    currentChar = sourceFile.getSource();
  }

  // scanSeparator skips a single separator.

  private void scanSeparator() {
    switch (currentChar) {
    case '!':
      {
        takeIt();
        while ((currentChar != SourceFile.EOL) && (currentChar != SourceFile.EOT))
          takeIt();
        if (currentChar == SourceFile.EOL)
          takeIt();
        writerHTML.commentedWrite(currentSpelling.toString());
      }
      break;
    case ' ':
      writerHTML.writeSpace();
      takeIt();
      break;
    case '\n':
      writerHTML.writeNewLine();
      takeIt();
      break;
    case '\r':
      takeIt();
      break;
    case '\t':
      writerHTML.writeTab();
      takeIt();
      break;
    }
  }

  private int scanToken() {

    switch (currentChar) {

    case 'a':  case 'b':  case 'c':  case 'd':  case 'e':
    case 'f':  case 'g':  case 'h':  case 'i':  case 'j':
    case 'k':  case 'l':  case 'm':  case 'n':  case 'o':
    case 'p':  case 'q':  case 'r':  case 's':  case 't':
    case 'u':  case 'v':  case 'w':  case 'x':  case 'y':
    case 'z':
    case 'A':  case 'B':  case 'C':  case 'D':  case 'E':
    case 'F':  case 'G':  case 'H':  case 'I':  case 'J':
    case 'K':  case 'L':  case 'M':  case 'N':  case 'O':
    case 'P':  case 'Q':  case 'R':  case 'S':  case 'T':
    case 'U':  case 'V':  case 'W':  case 'X':  case 'Y':
    case 'Z':
      takeIt();
      while (isLetter(currentChar) || isDigit(currentChar))
        takeIt();
      return Token.IDENTIFIER;

    case '0':  case '1':  case '2':  case '3':  case '4':
    case '5':  case '6':  case '7':  case '8':  case '9':
      takeIt();
      while (isDigit(currentChar))
        takeIt();
      return Token.INTLITERAL;

    case '+':  case '-':  case '*': case '/':  case '=':
    case '<':  case '>':  case '\\':  case '&':  case '@':
    case '%':  case '^':  case '?':
      takeIt();
      while (isOperator(currentChar))
        takeIt();
      return Token.OPERATOR;

    case '\'':
      takeIt();
      takeIt(); // the quoted character
      if (currentChar == '\'') {
      	takeIt();
        return Token.CHARLITERAL;
      } else
        return Token.ERROR;

    case '.':
      takeIt();
      return Token.DOT;
    
    case '$':
      takeIt();
      return Token.DENOTE;

    case '|':
      takeIt();
      return Token.OR;

    case ':':
      takeIt();
      if (currentChar == '=') {
        takeIt();
        return Token.BECOMES;
      } else
        return Token.COLON;

    case ';':
      takeIt();
      return Token.SEMICOLON;

    case ',':
      takeIt();
      return Token.COMMA;

    case '~':
      takeIt();
      return Token.IS;

    case '(':
      takeIt();
      return Token.LPAREN;

    case ')':
      takeIt();
      return Token.RPAREN;

    case '[':
      takeIt();
      return Token.LBRACKET;

    case ']':
      takeIt();
      return Token.RBRACKET;

    case '{':
      takeIt();
      return Token.LCURLY;

    case '}':
      takeIt();
      return Token.RCURLY;

    case SourceFile.EOT:
      return Token.EOT;

    default:
      takeIt();
      return Token.ERROR;
    }
  }

  public Token scan () {
    Token tok;
    SourcePosition pos;
    int kind;

    currentSpelling = new StringBuffer("");
    while (currentChar == '!'
           || currentChar == ' '
           || currentChar == '\n'
           || currentChar == '\r'
           || currentChar == '\t')
      scanSeparator();

    currentSpelling = new StringBuffer("");
    pos = new SourcePosition();
    pos.start = sourceFile.getCurrentLine();

    kind = scanToken();

    pos.finish = sourceFile.getCurrentLine();
    tok = new Token(kind, currentSpelling.toString(), pos);
    
    if(tok.kind == Token.IDENTIFIER ||
       tok.kind == Token.OPERATOR   ||
      (Token.DOT <= tok.kind && tok.kind <= Token.RCURLY)) {
      writerHTML.defaultWrite(tok.spelling);
    } else if (Token.ARRAY <= tok.kind && tok.kind <= Token.WHILE) {
      writerHTML.reservedWrite(tok.spelling);
    } else if (tok.kind == Token.INTLITERAL ||
               tok.kind == Token.CHARLITERAL) {
      writerHTML.literalWrite(tok.spelling);
    }
    return tok;
  }

  public void exportHTML() {
    currentToken = scan();
    while(currentToken.kind != Token.EOT) {
      currentToken = scan();
    }
    try {
      writerHTML.exportFile();
      System.out.println("exporting HTML was successful");
    } catch (IOException e) {
      System.out.println("Error while exporting HTML");
    }
  }

}
