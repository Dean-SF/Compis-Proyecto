/*
 * @(#)Parser.java                        2.1 2003/10/07
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
import Triangle.ErrorReporter;
import Triangle.AbstractSyntaxTrees.ActualParameter;
import Triangle.AbstractSyntaxTrees.ActualParameterSequence;
import Triangle.AbstractSyntaxTrees.ArrayAggregate;
import Triangle.AbstractSyntaxTrees.ArrayExpression;
import Triangle.AbstractSyntaxTrees.ArrayTypeDenoter;
import Triangle.AbstractSyntaxTrees.AssignCommand;
import Triangle.AbstractSyntaxTrees.BinaryExpression;
import Triangle.AbstractSyntaxTrees.CallCommand;
import Triangle.AbstractSyntaxTrees.CallExpression;
import Triangle.AbstractSyntaxTrees.CharacterExpression;
import Triangle.AbstractSyntaxTrees.CharacterLiteral;
import Triangle.AbstractSyntaxTrees.Command;
import Triangle.AbstractSyntaxTrees.CompoundLongIdentifier;
import Triangle.AbstractSyntaxTrees.CompoundProgram;
import Triangle.AbstractSyntaxTrees.CompoundVname;
import Triangle.AbstractSyntaxTrees.ConstActualParameter;
import Triangle.AbstractSyntaxTrees.ConstDeclaration;
import Triangle.AbstractSyntaxTrees.ConstFormalParameter;
import Triangle.AbstractSyntaxTrees.Declaration;
import Triangle.AbstractSyntaxTrees.DotVarname;
import Triangle.AbstractSyntaxTrees.EmptyActualParameterSequence;
import Triangle.AbstractSyntaxTrees.SkipCommand;
import Triangle.AbstractSyntaxTrees.SubscriptVarname;
import Triangle.AbstractSyntaxTrees.EmptyFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.Expression;
import Triangle.AbstractSyntaxTrees.FieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.ForCommand;
import Triangle.AbstractSyntaxTrees.ForUntilCommand;
import Triangle.AbstractSyntaxTrees.ForWhileCommand;
import Triangle.AbstractSyntaxTrees.FormalParameter;
import Triangle.AbstractSyntaxTrees.FormalParameterSequence;
import Triangle.AbstractSyntaxTrees.FuncActualParameter;
import Triangle.AbstractSyntaxTrees.FuncFormalParameter;
import Triangle.AbstractSyntaxTrees.FunctionProc_Funcs;
import Triangle.AbstractSyntaxTrees.Identifier;
import Triangle.AbstractSyntaxTrees.IfCommand;
import Triangle.AbstractSyntaxTrees.IfExpression;
import Triangle.AbstractSyntaxTrees.IntegerExpression;
import Triangle.AbstractSyntaxTrees.IntegerLiteral;
import Triangle.AbstractSyntaxTrees.LetCommand;
import Triangle.AbstractSyntaxTrees.LetExpression;
import Triangle.AbstractSyntaxTrees.LongIdentifier;
import Triangle.AbstractSyntaxTrees.MultipleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleArrayAggregate;
import Triangle.AbstractSyntaxTrees.MultipleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.MultipleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleRecordAggregate;
import Triangle.AbstractSyntaxTrees.Operator;
import Triangle.AbstractSyntaxTrees.Package;
import Triangle.AbstractSyntaxTrees.PackageDeclaration;
import Triangle.AbstractSyntaxTrees.PrivDeclaration;
import Triangle.AbstractSyntaxTrees.ProcActualParameter;
import Triangle.AbstractSyntaxTrees.ProcFormalParameter;
import Triangle.AbstractSyntaxTrees.Proc_Funcs;
import Triangle.AbstractSyntaxTrees.ProcedureProc_Funcs;
import Triangle.AbstractSyntaxTrees.Program;
import Triangle.AbstractSyntaxTrees.RecDeclaration;
import Triangle.AbstractSyntaxTrees.RecordAggregate;
import Triangle.AbstractSyntaxTrees.RecordExpression;
import Triangle.AbstractSyntaxTrees.RecordTypeDenoter;
import Triangle.AbstractSyntaxTrees.RepeatDoUntilCommand;
import Triangle.AbstractSyntaxTrees.RepeatDoWhileCommand;
import Triangle.AbstractSyntaxTrees.RepeatTimesCommand;
import Triangle.AbstractSyntaxTrees.RepeatUntilCommand;
import Triangle.AbstractSyntaxTrees.RepeatWhileCommand;
import Triangle.AbstractSyntaxTrees.SequentialCommand;
import Triangle.AbstractSyntaxTrees.SequentialDeclaration;
import Triangle.AbstractSyntaxTrees.SequentialPackageDeclaration;
import Triangle.AbstractSyntaxTrees.SequentialProcFuncs;
import Triangle.AbstractSyntaxTrees.SimpleLongIdentifier;
import Triangle.AbstractSyntaxTrees.SimpleProgram;
import Triangle.AbstractSyntaxTrees.SimpleTypeDenoter;
import Triangle.AbstractSyntaxTrees.SimpleVarname;
import Triangle.AbstractSyntaxTrees.SimpleVname;
import Triangle.AbstractSyntaxTrees.SingleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleArrayAggregate;
import Triangle.AbstractSyntaxTrees.SingleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.SingleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleRecordAggregate;
import Triangle.AbstractSyntaxTrees.TypeDeclaration;
import Triangle.AbstractSyntaxTrees.TypeDenoter;
import Triangle.AbstractSyntaxTrees.UnaryExpression;
import Triangle.AbstractSyntaxTrees.VarActualParameter;
import Triangle.AbstractSyntaxTrees.VarDeclaration;
import Triangle.AbstractSyntaxTrees.VarFormalParameter;
import Triangle.AbstractSyntaxTrees.Varname;
import Triangle.AbstractSyntaxTrees.Vname;
import Triangle.AbstractSyntaxTrees.VnameExpression;

public class Parser {

  private Scanner lexicalAnalyser;
  private ErrorReporter errorReporter;
  private Token currentToken;
  private SourcePosition previousTokenPosition;

  public Parser(Scanner lexer, ErrorReporter reporter) {
    lexicalAnalyser = lexer;
    errorReporter = reporter;
    previousTokenPosition = new SourcePosition();
  }

// accept checks whether the current token matches tokenExpected.
// If so, fetches the next token.
// If not, reports a syntactic error.

  void accept (int tokenExpected) throws SyntaxError {
    if (currentToken.kind == tokenExpected) {
      previousTokenPosition = currentToken.position;
      currentToken = lexicalAnalyser.scan();
    } else {
      syntacticError("\"%\" expected here", Token.spell(tokenExpected));
    }
  }

  void acceptIt() {
    previousTokenPosition = currentToken.position;
    currentToken = lexicalAnalyser.scan();
  }

// start records the position of the start of a phrase.
// This is defined to be the position of the first
// character of the first token of the phrase.

  void start(SourcePosition position) {
    position.start = currentToken.position.start;
  }

// finish records the position of the end of a phrase.
// This is defined to be the position of the last
// character of the last token of the phrase.

  void finish(SourcePosition position) {
    position.finish = previousTokenPosition.finish;
  }

  void syntacticError(String messageTemplate, String tokenQuoted) throws SyntaxError {
    SourcePosition pos = currentToken.position;
    errorReporter.reportError(messageTemplate, tokenQuoted, pos);
    throw(new SyntaxError());
  }

///////////////////////////////////////////////////////////////////////////////
//
// PROGRAMS
//
///////////////////////////////////////////////////////////////////////////////

public Program parseProgram() {
 
  Program programAST = null;

  previousTokenPosition.start = 0;
  previousTokenPosition.finish = 0;
  currentToken = lexicalAnalyser.scan();

  try {
    if(currentToken.kind == Token.PACKAGE) {
      Package pAST = parsePackage();
      Command cAST = parseCommand();
      programAST = new CompoundProgram(pAST, cAST, previousTokenPosition);
      
    } else {
      Command cAST = parseCommand();
      programAST = new SimpleProgram(cAST, previousTokenPosition);
    }
    
    if (currentToken.kind != Token.EOT) {
      syntacticError("\"%\" not expected after end of program",
        currentToken.spelling);
    }
  }
  catch (SyntaxError s) { return null; }
  return programAST;
}

public Package parsePackage() throws SyntaxError{
  Package pAST = null;
  SourcePosition packagePos = new SourcePosition();
  start(packagePos);
  pAST = parsePackageDeclaration();
  while(currentToken.kind == Token.PACKAGE) {
    Package pdAST = parsePackageDeclaration();
    finish(packagePos);
    pAST = new SequentialPackageDeclaration(pAST, pdAST, packagePos);
  }
  return pAST;
} 

///////////////////////////////////////////////////////////////////////////////
//
// LITERALS
//
///////////////////////////////////////////////////////////////////////////////

// parseIntegerLiteral parses an integer-literal, and constructs
// a leaf AST to represent it.

  IntegerLiteral parseIntegerLiteral() throws SyntaxError {
    IntegerLiteral IL = null;

    if (currentToken.kind == Token.INTLITERAL) {
      previousTokenPosition = currentToken.position;
      String spelling = currentToken.spelling;
      IL = new IntegerLiteral(spelling, previousTokenPosition);
      currentToken = lexicalAnalyser.scan();
    } else {
      IL = null;
      syntacticError("integer literal expected here", "");
    }
    return IL;
  }

// parseCharacterLiteral parses a character-literal, and constructs a leaf
// AST to represent it.

  CharacterLiteral parseCharacterLiteral() throws SyntaxError {
    CharacterLiteral CL = null;

    if (currentToken.kind == Token.CHARLITERAL) {
      previousTokenPosition = currentToken.position;
      String spelling = currentToken.spelling;
      CL = new CharacterLiteral(spelling, previousTokenPosition);
      currentToken = lexicalAnalyser.scan();
    } else {
      CL = null;
      syntacticError("character literal expected here", "");
    }
    return CL;
  }

// parseIdentifier parses an identifier, and constructs a leaf AST to
// represent it.

  Identifier parseIdentifier() throws SyntaxError {
    Identifier I = null;

    if (currentToken.kind == Token.IDENTIFIER) {
      previousTokenPosition = currentToken.position;
      String spelling = currentToken.spelling;
      I = new Identifier(spelling, previousTokenPosition);
      currentToken = lexicalAnalyser.scan();
    } else {
      I = null;
      syntacticError("identifier expected here", "");
    }
    return I;
  }

// parseOperator parses an operator, and constructs a leaf AST to
// represent it.

  Operator parseOperator() throws SyntaxError {
    Operator O = null;

    if (currentToken.kind == Token.OPERATOR) {
      previousTokenPosition = currentToken.position;
      String spelling = currentToken.spelling;
      O = new Operator(spelling, previousTokenPosition);
      currentToken = lexicalAnalyser.scan();
    } else {
      O = null;
      syntacticError("operator expected here", "");
    }
    return O;
  }

///////////////////////////////////////////////////////////////////////////////
//
// LONG-IDENTIFIER
//
///////////////////////////////////////////////////////////////////////////////

  LongIdentifier parseLongIdentifier() throws SyntaxError {
    LongIdentifier liAST = null;
    Identifier iAST = parseIdentifier();
    liAST = parseRestOfLongIdentifier(iAST);
    return liAST;
  }

  LongIdentifier parseRestOfLongIdentifier(Identifier iAST) throws SyntaxError {
    SourcePosition commandPos = new SourcePosition();
    commandPos = iAST.position;
    
    if(currentToken.kind == Token.DENOTE) {
      acceptIt();;
      Identifier i2AST = parseIdentifier();
      finish(commandPos);
      return new CompoundLongIdentifier(iAST, i2AST, commandPos);
      
    } else {
      finish(commandPos);
      return new SimpleLongIdentifier(iAST, commandPos);
    }
  }


///////////////////////////////////////////////////////////////////////////////
//
// COMMANDS
//
///////////////////////////////////////////////////////////////////////////////

// parseCommand parses the command, and constructs an AST
// to represent its phrase structure.

  Command parseCommand() throws SyntaxError {
    Command commandAST = null; // in case there's a syntactic error

    SourcePosition commandPos = new SourcePosition();

    start(commandPos);
    commandAST = parseSingleCommand();
    while (currentToken.kind == Token.SEMICOLON) {
      acceptIt();
      Command c2AST = parseSingleCommand();
      finish(commandPos);
      commandAST = new SequentialCommand(commandAST, c2AST, commandPos);
    }
    return commandAST;
  }

  Command parseSingleCommand() throws SyntaxError {
    Command commandAST = null; // in case there's a syntactic error

    SourcePosition commandPos = new SourcePosition();
    start(commandPos);

    switch (currentToken.kind) {

    case Token.IDENTIFIER:{
        Identifier iAST = parseIdentifier();
        LongIdentifier liAST = parseRestOfLongIdentifier(iAST);
        Vname vAST = null;
        Expression eAST = null;

        switch (currentToken.kind) {
          case Token.BECOMES:
            vAST = parseSpecialVnameCase(liAST);
            acceptIt();
            eAST = parseExpression();
            finish(commandPos);
            commandAST = new AssignCommand(vAST, eAST, commandPos);
            break;
          case Token.DOT:
          case Token.LBRACKET:
            vAST = parseSpecialVnameCase(liAST);
            accept(Token.BECOMES);
            eAST = parseExpression();
            finish(commandPos);
            commandAST = new AssignCommand(vAST, eAST, commandPos);
            break;
          case Token.LPAREN:
            acceptIt();
            ActualParameterSequence apsAST = parseActualParameterSequence();
            accept(Token.RPAREN);
            finish(commandPos);
            commandAST = new CallCommand(liAST, apsAST, commandPos);
            break;
          default:
            syntacticError("\"%\" cannot continue a Assign command",
            currentToken.spelling);
            break;
          
        }
      }
      break;
    
    case Token.SKIP: {
      acceptIt();
      finish(commandPos);
      commandAST = new SkipCommand(commandPos);
      break;
    }

    //Ericka "let" Declaration "in" Command "end"
    case Token.LET:
      {
        acceptIt();
        Declaration dAST = parseDeclaration();
        accept(Token.IN);
        Command cAST = parseCommand();
        accept(Token.END);
        finish(commandPos);
        commandAST = new LetCommand(dAST, cAST, commandPos);
        break;
      }
      
    
    case Token.IF: {
      acceptIt();
      Expression eAST = parseExpression();
      accept(Token.THEN);
      Command acceptCommandAST = parseCommand();
      Command elseCommandAST = ifChainParser();
      accept(Token.END);
      finish(commandPos);
      commandAST = new IfCommand(eAST, acceptCommandAST, elseCommandAST, commandPos);
      break;
    }

    case Token.REPEAT: {
      Expression eAST = null;
      Command cAST = null;
      acceptIt();
      switch (currentToken.kind) {
        case Token.WHILE:
          acceptIt();
          eAST = parseExpression();
          accept(Token.DO);
          cAST = parseCommand();
          accept(Token.END);
          finish(commandPos);
          commandAST = new RepeatWhileCommand(eAST, cAST, commandPos);
          break;
        case Token.UNTIL:
          acceptIt();
          eAST = parseExpression();
          accept(Token.DO);
          cAST = parseCommand();
          accept(Token.END);
          finish(commandPos);
          commandAST = new RepeatUntilCommand(eAST, cAST, commandPos);
          break;
        case Token.DO:
          acceptIt();
          cAST = parseCommand();
          switch (currentToken.kind) {
            case Token.WHILE:
              acceptIt();
              eAST = parseExpression();
              accept(Token.END);
              finish(commandPos);
              commandAST = new RepeatDoWhileCommand(eAST, cAST, commandPos);
              break;
            
            case Token.UNTIL:
              acceptIt();
              eAST = parseExpression();
              accept(Token.END);
              finish(commandPos);
              commandAST = new RepeatDoUntilCommand(eAST, cAST, commandPos);
              break;
          
            default:
              syntacticError("\"%\" cannot continue a Repeat Do command",
              currentToken.spelling);
              break;
          }

          break;
        
        default:
          eAST = parseExpression();
          accept(Token.TIMES);
          accept(Token.DO);
          cAST = parseCommand();
          accept(Token.END);
          finish(commandPos);
          commandAST = new RepeatTimesCommand(eAST, cAST, commandPos);
          break;
      }
      break;
    }

    case Token.FOR: {
      Identifier iAST = null;
      Expression e1AST, e2AST, e3AST = null;
      Command cAST = null;
      acceptIt();
      iAST = parseIdentifier();
      accept(Token.BECOMES);
      e1AST = parseExpression();
      accept(Token.DOT);
      accept(Token.DOT);
      e2AST = parseExpression();
      switch (currentToken.kind) {
        case Token.WHILE:
          acceptIt();
          e3AST = parseExpression();
          accept(Token.DO);
          cAST = parseCommand();
          accept(Token.END);
          finish(commandPos);
          commandAST = new ForWhileCommand(iAST, e1AST, e2AST, e3AST, cAST, commandPos);
          break;
        
        case Token.UNTIL:
          acceptIt();
          e3AST = parseExpression();
          accept(Token.DO);
          cAST = parseCommand();
          accept(Token.END);
          finish(commandPos);
          commandAST = new ForUntilCommand(iAST, e1AST, e2AST, e3AST, cAST, commandPos);
          break;
        case Token.DO:
          accept(Token.DO);
          cAST = parseCommand();
          accept(Token.END);
          finish(commandPos);
          commandAST = new ForCommand(iAST, e1AST, e2AST, cAST, commandPos);
          break;
        default:
          syntacticError("\"%\" cannot continue a For Command",
          currentToken.spelling);
          break;
      }
      break;
    }

    /*
    case Token.BEGIN:
      acceptIt();
      commandAST = parseCommand();
      accept(Token.END);
      break;

    case Token.LET:
      {
        acceptIt();
        Declaration dAST = parseDeclaration();
        accept(Token.IN);
        Command cAST = parseSingleCommand();
        finish(commandPos);
        commandAST = new LetCommand(dAST, cAST, commandPos);
      }
      break;

    case Token.IF:
      {
        acceptIt();
        Expression eAST = parseExpression();
        accept(Token.THEN);
        Command c1AST = parseSingleCommand();
        accept(Token.ELSE);
        Command c2AST = parseSingleCommand();
        finish(commandPos);
        commandAST = new IfCommand(eAST, c1AST, c2AST, commandPos);
      }
      break;

    case Token.WHILE:
      {
        acceptIt();
        Expression eAST = parseExpression();
        accept(Token.DO);
        Command cAST = parseSingleCommand();
        finish(commandPos);
        commandAST = new WhileCommand(eAST, cAST, commandPos);
      }
      break;

    case Token.SEMICOLON:
    case Token.END:
    case Token.ELSE:
    case Token.IN:
    case Token.EOT:

      finish(commandPos);
      commandAST = new EmptyCommand(commandPos);
      break;*/

    default:
      syntacticError("\"%\" cannot start a command",
        currentToken.spelling);
      break;

    }

    return commandAST;
  }

  Command ifChainParser() throws SyntaxError {
    Command ifChainAST = null;
    SourcePosition commandPos = new SourcePosition();
    start(commandPos);

    switch (currentToken.kind) {
      case Token.OR:
        acceptIt();
        Expression eAST = parseExpression();
        accept(Token.THEN);
        Command acceptCommandAST = parseCommand();
        Command elseCommandAST = ifChainParser();
        finish(commandPos);
        ifChainAST = new IfCommand(eAST, acceptCommandAST, elseCommandAST, commandPos);
        break;
      
      case Token.ELSE:
        acceptIt();
        ifChainAST = parseCommand();
        break;

      default:
          syntacticError("\"%\" cannot be used to continue the if command",
          currentToken.spelling);
        break;
    }

    return ifChainAST;
  }

///////////////////////////////////////////////////////////////////////////////
//
// EXPRESSIONS
//
///////////////////////////////////////////////////////////////////////////////

  Expression parseExpression() throws SyntaxError {
    Expression expressionAST = null; // in case there's a syntactic error

    SourcePosition expressionPos = new SourcePosition();

    start (expressionPos);

    switch (currentToken.kind) {

    case Token.LET:
      {
        acceptIt();
        Declaration dAST = parseDeclaration();
        accept(Token.IN);
        Expression eAST = parseExpression();
        finish(expressionPos);
        expressionAST = new LetExpression(dAST, eAST, expressionPos);
      }
      break;

    case Token.IF:
      {
        acceptIt();
        Expression e1AST = parseExpression();
        accept(Token.THEN);
        Expression e2AST = parseExpression();
        accept(Token.ELSE);
        Expression e3AST = parseExpression();
        finish(expressionPos);
        expressionAST = new IfExpression(e1AST, e2AST, e3AST, expressionPos);
      }
      break;

    default:
      expressionAST = parseSecondaryExpression();
      break;
    }
    return expressionAST;
  }

  Expression parseSecondaryExpression() throws SyntaxError {
    Expression expressionAST = null; // in case there's a syntactic error

    SourcePosition expressionPos = new SourcePosition();
    start(expressionPos);

    expressionAST = parsePrimaryExpression();
    while (currentToken.kind == Token.OPERATOR) {
      Operator opAST = parseOperator();
      Expression e2AST = parsePrimaryExpression();
      expressionAST = new BinaryExpression (expressionAST, opAST, e2AST,
        expressionPos);
    }
    return expressionAST;
  }

  Expression parsePrimaryExpression() throws SyntaxError {
    Expression expressionAST = null; // in case there's a syntactic error

    SourcePosition expressionPos = new SourcePosition();
    start(expressionPos);

    switch (currentToken.kind) {

    case Token.INTLITERAL:
      {
        IntegerLiteral ilAST = parseIntegerLiteral();
        finish(expressionPos);
        expressionAST = new IntegerExpression(ilAST, expressionPos);
      }
      break;

    case Token.CHARLITERAL:
      {
        CharacterLiteral clAST= parseCharacterLiteral();
        finish(expressionPos);
        expressionAST = new CharacterExpression(clAST, expressionPos);
      }
      break;

    case Token.LBRACKET:
      {
        acceptIt();
        ArrayAggregate aaAST = parseArrayAggregate();
        accept(Token.RBRACKET);
        finish(expressionPos);
        expressionAST = new ArrayExpression(aaAST, expressionPos);
      }
      break;

    case Token.LCURLY:
      {
        acceptIt();
        RecordAggregate raAST = parseRecordAggregate();
        accept(Token.RCURLY);
        finish(expressionPos);
        expressionAST = new RecordExpression(raAST, expressionPos);
      }
      break;

    case Token.IDENTIFIER:
      {
        Identifier iAST= parseIdentifier();
        LongIdentifier liAST = parseRestOfLongIdentifier(iAST);
        if (currentToken.kind == Token.LPAREN) {
          acceptIt();
          ActualParameterSequence apsAST = parseActualParameterSequence();
          accept(Token.RPAREN);
          finish(expressionPos);
          expressionAST = new CallExpression(liAST, apsAST, expressionPos);

        } else {
          Vname vAST = parseRestOfVname(iAST);
          finish(expressionPos);
          expressionAST = new VnameExpression(vAST, expressionPos);
        }
      }
      break;

    case Token.OPERATOR:
      {
        Operator opAST = parseOperator();
        Expression eAST = parsePrimaryExpression();
        finish(expressionPos);
        expressionAST = new UnaryExpression(opAST, eAST, expressionPos);
      }
      break;

    case Token.LPAREN:
      acceptIt();
      expressionAST = parseExpression();
      accept(Token.RPAREN);
      break;

    default:
      syntacticError("\"%\" cannot start an expression",
        currentToken.spelling);
      break;

    }
    return expressionAST;
  }

  RecordAggregate parseRecordAggregate() throws SyntaxError {
    RecordAggregate aggregateAST = null; // in case there's a syntactic error

    SourcePosition aggregatePos = new SourcePosition();
    start(aggregatePos);

    Identifier iAST = parseIdentifier();
    accept(Token.IS);
    Expression eAST = parseExpression();

    if (currentToken.kind == Token.COMMA) {
      acceptIt();
      RecordAggregate aAST = parseRecordAggregate();
      finish(aggregatePos);
      aggregateAST = new MultipleRecordAggregate(iAST, eAST, aAST, aggregatePos);
    } else {
      finish(aggregatePos);
      aggregateAST = new SingleRecordAggregate(iAST, eAST, aggregatePos);
    }
    return aggregateAST;
  }

  ArrayAggregate parseArrayAggregate() throws SyntaxError {
    ArrayAggregate aggregateAST = null; // in case there's a syntactic error

    SourcePosition aggregatePos = new SourcePosition();
    start(aggregatePos);

    Expression eAST = parseExpression();
    if (currentToken.kind == Token.COMMA) {
      acceptIt();
      ArrayAggregate aAST = parseArrayAggregate();
      finish(aggregatePos);
      aggregateAST = new MultipleArrayAggregate(eAST, aAST, aggregatePos);
    } else {
      finish(aggregatePos);
      aggregateAST = new SingleArrayAggregate(eAST, aggregatePos);
    }
    return aggregateAST;
  }

///////////////////////////////////////////////////////////////////////////////
//
// VALUE-OR-VARIABLE NAMES
//
///////////////////////////////////////////////////////////////////////////////

  /*
  Vname parseSpecialVnameCase (Identifier i1AST, Identifier i2AST, boolean simple) throws SyntaxError  {
    Vname vAST = null;
    
    SourcePosition vnamePos1 = new SourcePosition();
    vnamePos1 = i1AST.position;

    Varname varAST = parseRestOfVarName(i2AST);

    finish(vnamePos1);
    if(simple) {
      vAST = new SimpleVname(varAST, vnamePos1);
    } else {
      vAST = new CompoundVname(i1AST, varAST, vnamePos1);
    }
    return vAST;
  }*/

  Vname parseSpecialVnameCase (LongIdentifier liAST) throws SyntaxError  {
    Vname vAST = null;
    SourcePosition vnamePos = new SourcePosition();
    if(liAST instanceof CompoundLongIdentifier) {
      Identifier i1AST = ((CompoundLongIdentifier)liAST).I1;
      Identifier i2AST = ((CompoundLongIdentifier)liAST).I2;
      vnamePos = i1AST.position;
      Varname varAST = parseRestOfVarName(i2AST);
      finish(vnamePos);
      vAST = new CompoundVname(i1AST, varAST, vnamePos);
    } else if(liAST instanceof SimpleLongIdentifier) {
      Identifier iAST = ((SimpleLongIdentifier)liAST).I;
      vnamePos = iAST.position;
      Varname varAST = parseRestOfVarName(iAST);
      finish(vnamePos);
      vAST = new SimpleVname(varAST, vnamePos);
    }
    return vAST;
  }

  Vname parseVname() throws SyntaxError {
    Vname vnameAST = null; // in case there's a syntactic error
    Identifier iAST = parseIdentifier();
    vnameAST = parseRestOfVname(iAST);
    return vnameAST;
  }

  Vname parseRestOfVname(Identifier iAST) throws SyntaxError {
    SourcePosition commandPos = new SourcePosition();
    commandPos = iAST.position;
    
    if(currentToken.kind == Token.DENOTE) {
      acceptIt();;
      Varname varAST = parseVarName();
      finish(commandPos);
      return new CompoundVname(iAST, varAST, commandPos);
      
    } else {
      Varname varAST = parseRestOfVarName(iAST);
      finish(commandPos);
      return new SimpleVname(varAST, commandPos);
    }
  }

  Varname parseVarName () throws SyntaxError {
    Varname varnameAST = null; // in case there's a syntactic error
    Identifier iAST = parseIdentifier();
    varnameAST = parseRestOfVarName(iAST);
    return varnameAST;
  }

  Varname parseRestOfVarName(Identifier identifierAST) throws SyntaxError {
    SourcePosition varnamePos = new SourcePosition();
    varnamePos = identifierAST.position;
    Varname vAST = new SimpleVarname(identifierAST, varnamePos);

    while (currentToken.kind == Token.DOT ||
           currentToken.kind == Token.LBRACKET) {

      if (currentToken.kind == Token.DOT) {
        acceptIt();
        Identifier iAST = parseIdentifier();
        vAST = new DotVarname(vAST, iAST, varnamePos);
      } else {
        acceptIt();
        Expression eAST = parseExpression();
        accept(Token.RBRACKET);
        finish(varnamePos);
        vAST = new SubscriptVarname(vAST, eAST, varnamePos);
      }
    }
    return vAST;
  }

///////////////////////////////////////////////////////////////////////////////
//
// DECLARATIONS
//
///////////////////////////////////////////////////////////////////////////////

//Ericka

  Declaration parseCompound_Declaration() throws SyntaxError{
    Declaration declarationAST = null; // in case there's a syntactic error

    SourcePosition declarationPos = new SourcePosition();
    start(declarationPos);
    
    switch (currentToken.kind){

      case Token.REC:{
        acceptIt();
        Proc_Funcs pfsAST = parseProcFuncs();
        accept(Token.END);
        finish(declarationPos);
        declarationAST = new RecDeclaration(pfsAST, declarationPos);
        break;
      }

      case Token.PRIVATE:{
        acceptIt();
        Declaration dAST = parseDeclaration();
        accept(Token.IN);
        Declaration d2AST = parseDeclaration();
        accept(Token.END);
        finish(declarationPos);
        declarationAST = new PrivDeclaration(dAST, d2AST, declarationPos);
        break;
      }

      case Token.CONST:
      case Token.FUNC:
      case Token.PROC:
      case Token.TYPE:
      case Token.VAR:
        finish(declarationPos);
        declarationAST = parseSingleDeclaration();
        break;

      default:
        syntacticError("\"%\" cannot start a compound declaration", currentToken.spelling);
      break;
    }
    return declarationAST;
  }

  Declaration parseDeclaration() throws SyntaxError {
    Declaration declarationAST = null; // in case there's a syntactic error

    SourcePosition declarationPos = new SourcePosition();
    start(declarationPos);
    declarationAST = parseCompound_Declaration(); // Cambiando parseSingleDeclaration a parseCompound_Declaration - Ericka
    while (currentToken.kind == Token.SEMICOLON) {
      acceptIt();
      Declaration d2AST = parseCompound_Declaration();// Cambiando parseSingleDeclaration a parseCompound_Declaration - Ericka
      finish(declarationPos);
      declarationAST = new SequentialDeclaration(declarationAST, d2AST,
        declarationPos);
    }
    return declarationAST;
  }

  Declaration parseSingleDeclaration() throws SyntaxError {
    Declaration declarationAST = null; // in case there's a syntactic error

    SourcePosition declarationPos = new SourcePosition();
    start(declarationPos);

    switch (currentToken.kind) {

    case Token.CONST:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.IS);
        Expression eAST = parseExpression();
        finish(declarationPos);
        declarationAST = new ConstDeclaration(iAST, eAST, declarationPos);
      }
      break;

    case Token.VAR:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.COLON);
        TypeDenoter tAST = parseTypeDenoter();
        finish(declarationPos);
        declarationAST = new VarDeclaration(iAST, tAST, declarationPos);
      }
      break;

    case Token.TYPE:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.IS);
        TypeDenoter tAST = parseTypeDenoter();
        finish(declarationPos);
        declarationAST = new TypeDeclaration(iAST, tAST, declarationPos);
      }
      break;

    default:
      declarationAST = parseProcFunc(); //Ericka
      break;

    }
    return declarationAST;
  }
   /*
   * Andrea
   */
  Package parsePackageDeclaration() throws SyntaxError {
    Package packDeclarationAST = null; // in case there's a syntactic error

    SourcePosition packDeclarationPos = new SourcePosition();
    start(packDeclarationPos);

    if (currentToken.kind == Token.PACKAGE) {
      acceptIt();
      Identifier iAST = parseIdentifier();
      accept(Token.IS);
      Declaration dAST = parseDeclaration();
      accept(Token.END);
      finish(packDeclarationPos);
      packDeclarationAST = new PackageDeclaration(iAST, dAST, packDeclarationPos);
    
    } else {
      syntacticError("\"%\" cannot start a Package Declaration",
        currentToken.spelling);
    }
    
    return packDeclarationAST;
  }

///////////////////////////////////////////////////////////////////////////////
//
// PARAMETERS
//
///////////////////////////////////////////////////////////////////////////////

  FormalParameterSequence parseFormalParameterSequence() throws SyntaxError {
    FormalParameterSequence formalsAST;

    SourcePosition formalsPos = new SourcePosition();

    start(formalsPos);
    if (currentToken.kind == Token.RPAREN) {
      finish(formalsPos);
      formalsAST = new EmptyFormalParameterSequence(formalsPos);

    } else {
      formalsAST = parseProperFormalParameterSequence();
    }
    return formalsAST;
  }

  FormalParameterSequence parseProperFormalParameterSequence() throws SyntaxError {
    FormalParameterSequence formalsAST = null; // in case there's a syntactic error;

    SourcePosition formalsPos = new SourcePosition();
    start(formalsPos);
    FormalParameter fpAST = parseFormalParameter();
    if (currentToken.kind == Token.COMMA) {
      acceptIt();
      FormalParameterSequence fpsAST = parseProperFormalParameterSequence();
      finish(formalsPos);
      formalsAST = new MultipleFormalParameterSequence(fpAST, fpsAST,
        formalsPos);

    } else {
      finish(formalsPos);
      formalsAST = new SingleFormalParameterSequence(fpAST, formalsPos);
    }
    return formalsAST;
  }

  FormalParameter parseFormalParameter() throws SyntaxError {
    FormalParameter formalAST = null; // in case there's a syntactic error;

    SourcePosition formalPos = new SourcePosition();
    start(formalPos);

    switch (currentToken.kind) {

    case Token.IDENTIFIER:
      {
        Identifier iAST = parseIdentifier();
        accept(Token.COLON);
        TypeDenoter tAST = parseTypeDenoter();
        finish(formalPos);
        formalAST = new ConstFormalParameter(iAST, tAST, formalPos);
      }
      break;

    case Token.VAR:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.COLON);
        TypeDenoter tAST = parseTypeDenoter();
        finish(formalPos);
        formalAST = new VarFormalParameter(iAST, tAST, formalPos);
      }
      break;

    case Token.PROC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.LPAREN);
        FormalParameterSequence fpsAST = parseFormalParameterSequence();
        accept(Token.RPAREN);
        finish(formalPos);
        formalAST = new ProcFormalParameter(iAST, fpsAST, formalPos);
      }
      break;

    case Token.FUNC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.LPAREN);
        FormalParameterSequence fpsAST = parseFormalParameterSequence();
        accept(Token.RPAREN);
        accept(Token.COLON);
        TypeDenoter tAST = parseTypeDenoter();
        finish(formalPos);
        formalAST = new FuncFormalParameter(iAST, fpsAST, tAST, formalPos);
      }
      break;

    default:
      syntacticError("\"%\" cannot start a formal parameter",
        currentToken.spelling);
      break;

    }
    return formalAST;
  }


  ActualParameterSequence parseActualParameterSequence() throws SyntaxError {
    ActualParameterSequence actualsAST;

    SourcePosition actualsPos = new SourcePosition();

    start(actualsPos);
    if (currentToken.kind == Token.RPAREN) {
      finish(actualsPos);
      actualsAST = new EmptyActualParameterSequence(actualsPos);

    } else {
      actualsAST = parseProperActualParameterSequence();
    }
    return actualsAST;
  }

  ActualParameterSequence parseProperActualParameterSequence() throws SyntaxError {
    ActualParameterSequence actualsAST = null; // in case there's a syntactic error

    SourcePosition actualsPos = new SourcePosition();

    start(actualsPos);
    ActualParameter apAST = parseActualParameter();
    if (currentToken.kind == Token.COMMA) {
      acceptIt();
      ActualParameterSequence apsAST = parseProperActualParameterSequence();
      finish(actualsPos);
      actualsAST = new MultipleActualParameterSequence(apAST, apsAST,
        actualsPos);
    } else {
      finish(actualsPos);
      actualsAST = new SingleActualParameterSequence(apAST, actualsPos);
    }
    return actualsAST;
  }

  ActualParameter parseActualParameter() throws SyntaxError {
    ActualParameter actualAST = null; // in case there's a syntactic error

    SourcePosition actualPos = new SourcePosition();

    start(actualPos);

    switch (currentToken.kind) {

    case Token.IDENTIFIER:
    case Token.INTLITERAL:
    case Token.CHARLITERAL:
    case Token.OPERATOR:
    case Token.LET:
    case Token.IF:
    case Token.LPAREN:
    case Token.LBRACKET:
    case Token.LCURLY:
      {
        Expression eAST = parseExpression();
        finish(actualPos);
        actualAST = new ConstActualParameter(eAST, actualPos);
      }
      break;

    case Token.VAR:
      {
        acceptIt();
        Vname vAST = parseVname();
        finish(actualPos);
        actualAST = new VarActualParameter(vAST, actualPos);
      }
      break;

    case Token.PROC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        finish(actualPos);
        actualAST = new ProcActualParameter(iAST, actualPos);
      }
      break;

    case Token.FUNC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        finish(actualPos);
        actualAST = new FuncActualParameter(iAST, actualPos);
      }
      break;

    default:
      syntacticError("\"%\" cannot start an actual parameter",
        currentToken.spelling);
      break;

    }
    return actualAST;
  }

///////////////////////////////////////////////////////////////////////////////
//
// Proc_Func Ericka
//
///////////////////////////////////////////////////////////////////////////////

Proc_Funcs parseProcFuncs() throws SyntaxError{
  Proc_Funcs pfAST = null; //in case there's a syntactic error

  SourcePosition pfPos = new SourcePosition();
  
  start(pfPos);
  pfAST = parseProcFunc();
  do{
    acceptIt();
    Proc_Funcs pf2AST = parseProcFunc();
    finish(pfPos);
    pfAST = new SequentialProcFuncs(pfAST, pf2AST, pfPos);
  }while(currentToken.kind == Token.OR);
  return pfAST;
}

Proc_Funcs parseProcFunc() throws SyntaxError{
  Proc_Funcs pfAST = null; //in case there's a syntactic error
  SourcePosition pfPos = new SourcePosition();

  start (pfPos);

  switch (currentToken.kind){

    case Token.PROC:{
      acceptIt();
      Identifier iAST = parseIdentifier();
      accept(Token.LPAREN);
      FormalParameterSequence fpsAST = parseFormalParameterSequence();
      accept(Token.RPAREN);
      accept(Token.IS);
      Command cAST = parseCommand();
      accept(Token.END);
      finish(pfPos);
      pfAST = new ProcedureProc_Funcs(iAST, fpsAST, cAST, pfPos);
      break;
    }

    case Token.FUNC:{
      acceptIt();
      Identifier iAST = parseIdentifier();
      accept(Token.LPAREN);
      FormalParameterSequence fpsAST = parseFormalParameterSequence();
      accept(Token.RPAREN);
      accept(Token.COLON);
      TypeDenoter tAST = parseTypeDenoter();
      accept(Token.IS);
      Expression eAST = parseExpression();
      accept(Token.END);
      finish(pfPos);
      pfAST = new FunctionProc_Funcs(iAST, fpsAST, tAST, eAST, pfPos);
      break;
    }

    default:
      syntacticError("\"%\" cannot start a Proc Func",
        currentToken.spelling);
      break;
  }
  return pfAST;
}

///////////////////////////////////////////////////////////////////////////////
//
// TYPE-DENOTERS
//
///////////////////////////////////////////////////////////////////////////////

  TypeDenoter parseTypeDenoter() throws SyntaxError {
    TypeDenoter typeAST = null; // in case there's a syntactic error
    SourcePosition typePos = new SourcePosition();

    start(typePos);

    switch (currentToken.kind) {

    case Token.IDENTIFIER:
      {
        LongIdentifier lAST = parseLongIdentifier();
        finish(typePos);
        typeAST = new SimpleTypeDenoter(lAST, typePos);
        /*  Ericka
         * Identifier iAST = parseIdentifier();
         * finish(typePos);
         * typeAST = new SimpleTypeDenoter(iAST, typePos);
        */
      }
      break;

    case Token.ARRAY:
      {
        acceptIt();
        IntegerLiteral ilAST = parseIntegerLiteral();
        accept(Token.OF);
        TypeDenoter tAST = parseTypeDenoter();
        finish(typePos);
        typeAST = new ArrayTypeDenoter(ilAST, tAST, typePos);
      }
      break;

    case Token.RECORD:
      {
        acceptIt();
        FieldTypeDenoter fAST = parseFieldTypeDenoter();
        accept(Token.END);
        finish(typePos);
        typeAST = new RecordTypeDenoter(fAST, typePos);
      }
      break;

    default:
      syntacticError("\"%\" cannot start a type denoter",
        currentToken.spelling);
      break;

    }
    return typeAST;
  }

  FieldTypeDenoter parseFieldTypeDenoter() throws SyntaxError {
    FieldTypeDenoter fieldAST = null; // in case there's a syntactic error

    SourcePosition fieldPos = new SourcePosition();

    start(fieldPos);
    Identifier iAST = parseIdentifier();
    accept(Token.COLON);
    TypeDenoter tAST = parseTypeDenoter();
    if (currentToken.kind == Token.COMMA) {
      acceptIt();
      FieldTypeDenoter fAST = parseFieldTypeDenoter();
      finish(fieldPos);
      fieldAST = new MultipleFieldTypeDenoter(iAST, tAST, fAST, fieldPos);
    } else {
      finish(fieldPos);
      fieldAST = new SingleFieldTypeDenoter(iAST, tAST, fieldPos);
    }
    return fieldAST;
  }
}
