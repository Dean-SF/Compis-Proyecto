/*
 * @(#)Checker.java                        2.1 2003/10/07
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

package Triangle.ContextualAnalyzer;

import Triangle.ErrorReporter;
import Triangle.StdEnvironment;
import Triangle.AbstractSyntaxTrees.AnyTypeDenoter;
import Triangle.AbstractSyntaxTrees.ArrayExpression;
import Triangle.AbstractSyntaxTrees.ArrayTypeDenoter;
import Triangle.AbstractSyntaxTrees.AssignCommand;
import Triangle.AbstractSyntaxTrees.BinaryExpression;
import Triangle.AbstractSyntaxTrees.BinaryOperatorDeclaration;
import Triangle.AbstractSyntaxTrees.BoolTypeDenoter;
import Triangle.AbstractSyntaxTrees.CallCommand;
import Triangle.AbstractSyntaxTrees.CallExpression;
import Triangle.AbstractSyntaxTrees.CharTypeDenoter;
import Triangle.AbstractSyntaxTrees.CharacterExpression;
import Triangle.AbstractSyntaxTrees.CharacterLiteral;
import Triangle.AbstractSyntaxTrees.CompoundLongIdentifier;
import Triangle.AbstractSyntaxTrees.CompoundProgram;
import Triangle.AbstractSyntaxTrees.CompoundVname;
import Triangle.AbstractSyntaxTrees.ConstActualParameter;
import Triangle.AbstractSyntaxTrees.ConstDeclaration;
import Triangle.AbstractSyntaxTrees.ConstFormalParameter;
import Triangle.AbstractSyntaxTrees.Declaration;
import Triangle.AbstractSyntaxTrees.DotVarname;
import Triangle.AbstractSyntaxTrees.EmptyActualParameterSequence;
import Triangle.AbstractSyntaxTrees.EmptyExpression;
import Triangle.AbstractSyntaxTrees.EmptyFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.ErrorTypeDenoter;
import Triangle.AbstractSyntaxTrees.FieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.ForCommand;
import Triangle.AbstractSyntaxTrees.ForUntilCommand;
import Triangle.AbstractSyntaxTrees.ForWhileCommand;
import Triangle.AbstractSyntaxTrees.FormalParameter;
import Triangle.AbstractSyntaxTrees.FormalParameterSequence;
import Triangle.AbstractSyntaxTrees.FuncActualParameter;
import Triangle.AbstractSyntaxTrees.FuncDeclaration;
import Triangle.AbstractSyntaxTrees.FuncFormalParameter;
import Triangle.AbstractSyntaxTrees.FunctionProc_Funcs;
import Triangle.AbstractSyntaxTrees.Identifier;
import Triangle.AbstractSyntaxTrees.IfCommand;
import Triangle.AbstractSyntaxTrees.IfExpression;
import Triangle.AbstractSyntaxTrees.InitializedVarDeclaration;
import Triangle.AbstractSyntaxTrees.IntTypeDenoter;
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
import Triangle.AbstractSyntaxTrees.PackageDeclaration;
import Triangle.AbstractSyntaxTrees.PrivDeclaration;
import Triangle.AbstractSyntaxTrees.ProcActualParameter;
import Triangle.AbstractSyntaxTrees.ProcDeclaration;
import Triangle.AbstractSyntaxTrees.ProcFormalParameter;
import Triangle.AbstractSyntaxTrees.ProcedureProc_Funcs;
import Triangle.AbstractSyntaxTrees.Program;
import Triangle.AbstractSyntaxTrees.RecDeclaration;
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
import Triangle.AbstractSyntaxTrees.SkipCommand;
import Triangle.AbstractSyntaxTrees.SubscriptVarname;
import Triangle.AbstractSyntaxTrees.Terminal;
import Triangle.AbstractSyntaxTrees.TypeDeclaration;
import Triangle.AbstractSyntaxTrees.TypeDenoter;
import Triangle.AbstractSyntaxTrees.UnaryExpression;
import Triangle.AbstractSyntaxTrees.UnaryOperatorDeclaration;
import Triangle.AbstractSyntaxTrees.VarActualParameter;
import Triangle.AbstractSyntaxTrees.VarDeclaration;
import Triangle.AbstractSyntaxTrees.VarFormalParameter;
import Triangle.AbstractSyntaxTrees.Visitor;
import Triangle.AbstractSyntaxTrees.VnameExpression;
import Triangle.SyntacticAnalyzer.SourcePosition;

public final class Checker implements Visitor {

  /*
   * Metodo hecho por Ericka desde 0
   */
  @Override
  public Object visitSimpleProgram(SimpleProgram ast, Object o) { //Ericka
    ast.C.visit(this, null);
    return null;
  }

  /*
   * Metodo hecho por Ericka desde 0
   */
  @Override
  public Object visitRepeatWhileCommand(RepeatWhileCommand ast, Object o) { //Ericka
    TypeDenoter eType = (TypeDenoter)ast.E.visit(this, null);

    if (!eType.equals(StdEnvironment.booleanType))
      reporter.reportError("Boolean expression expected here", "", ast.E.position);

    ast.C.visit(this, null);

    return null;
  }

  /*
   * Metodo hecho por Ericka desde 0
   */
  @Override
  public Object visitRepeatUntilCommand(RepeatUntilCommand ast, Object o) {
    TypeDenoter eType = (TypeDenoter)ast.E.visit(this, null);

    if (!eType.equals(StdEnvironment.booleanType))
      reporter.reportError("Boolean expression expected here", "", ast.E.position);

    ast.C.visit(this, null);

    return null;
  }
  
  /*
   * Metodo hecho por Ericka desde 0
   */
  @Override
  public Object visitRepeatDoWhileCommand(RepeatDoWhileCommand ast, Object o) {
    TypeDenoter eType = (TypeDenoter)ast.E.visit(this, null);

    if (!eType.equals(StdEnvironment.booleanType))
      reporter.reportError("Boolean expression expected here", "", ast.E.position);

    ast.C.visit(this, null);

    return null;
  }

  /*
   * Metodo hecho por Ericka desde 0
   */
  @Override
  public Object visitRepeatDoUntilCommand(RepeatDoUntilCommand ast, Object o) {
    TypeDenoter eType = (TypeDenoter)ast.E.visit(this, null);

    if (!eType.equals(StdEnvironment.booleanType))
      reporter.reportError("Boolean expression expected here", "", ast.E.position);

    ast.C.visit(this, null);

    return null;
  }

  /*
   * Metodo hecho por Ericka desde 0
   */
  @Override
  public Object visitInitializedVarDeclaration(InitializedVarDeclaration ast, Object o) { //Ericka
    ast.T = (TypeDenoter)ast.E.visit(this, null);
    idTable.enter(ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError("identifier \"%\" already declared", ast.I.spelling, ast.position);
    return null;
  }

  /*
   * Metodo hecho por Deyan Sanabria desde 0
   */
  @Override
  public Object visitCompoundLongIdentifier(CompoundLongIdentifier ast, Object o) {
    if(!idTable.enterContext(ast.I1.spelling))
      reporter.reportError ("Package \"%\" is not declared",ast.I1.spelling, ast.I1.position);
    Declaration varBiding = (Declaration) ast.I2.visit(this, null);
    idTable.restoreContext();
    return varBiding;
  }

  /*
   * Metodo hecho por Deyan Sanabria desde 0
   */
  @Override
  public Object visitSimpleLongIdentifier(SimpleLongIdentifier ast, Object o) {
    return ast.I.visit(this, null);
  }
  
  /*
   * Metodo hecho por Deyan Sanabria desde 0
   */
  @Override
  public Object visitRepeatTimesCommand(RepeatTimesCommand ast, Object o) {
    TypeDenoter eType = (TypeDenoter)ast.E.visit(this, null);
    if (!eType.equals(StdEnvironment.integerType))
      reporter.reportError("Integer expression expected here", "", ast.E.position);
    
    ast.C.visit(this, null);

    return null;
  }

  /*
   * Metodo hecho por Deyan Sanabria desde 0
   */
  @Override
  public Object visitForCommand(ForCommand ast, Object o) {
    TypeDenoter e1Type = (TypeDenoter)ast.E1.visit(this, null);
    TypeDenoter e2Type = (TypeDenoter)ast.E2.visit(this, null);
    if(!e1Type.equals(StdEnvironment.integerType))
      reporter.reportError("Integer expression expected here", "", ast.E1.position);
    if(!e2Type.equals(StdEnvironment.integerType))
      reporter.reportError("Integer expression expected here", "", ast.E2.position);

    idTable.openScope();
    ast.I.decl = new VarDeclaration(ast.I, new IntTypeDenoter(dummyPos), true, ast.position);
    idTable.enter(ast.I.spelling, (Declaration) ast.I.decl);
    ast.C.visit(this, null);
    idTable.closeScope();
    return null;
  }

  /*
   * Metodo hecho por Deyan Sanabria desde 0
   */
  @Override
  public Object visitForWhileCommand(ForWhileCommand ast, Object o) {
    TypeDenoter e1Type = (TypeDenoter)ast.E1.visit(this, null);
    TypeDenoter e2Type = (TypeDenoter)ast.E2.visit(this, null);
    if(!e1Type.equals(StdEnvironment.integerType))
      reporter.reportError("Integer expression expected here", "", ast.E1.position);
    if(!e2Type.equals(StdEnvironment.integerType))
      reporter.reportError("Integer expression expected here", "", ast.E2.position);

    idTable.openScope();
    ast.I.decl = new VarDeclaration(ast.I, new IntTypeDenoter(dummyPos), true, ast.position);
    idTable.enter(ast.I.spelling, (Declaration) ast.I.decl);
    TypeDenoter e3Type = (TypeDenoter)ast.E3.visit(this, null);
    if(!e3Type.equals(StdEnvironment.booleanType))
      reporter.reportError("Boolean expression expected here", "", ast.E3.position);
    
    ast.C.visit(this, null);
    idTable.closeScope();
    return null;
  }

  /*
   * Metodo hecho por Deyan Sanabria desde 0
   */
  @Override
  public Object visitForUntilCommand(ForUntilCommand ast, Object o) {
    TypeDenoter e1Type = (TypeDenoter)ast.E1.visit(this, null);
    TypeDenoter e2Type = (TypeDenoter)ast.E2.visit(this, null);
    if(!e1Type.equals(StdEnvironment.integerType))
      reporter.reportError("Integer expression expected here", "", ast.E1.position);
    if(!e2Type.equals(StdEnvironment.integerType))
      reporter.reportError("Integer expression expected here", "", ast.E2.position);

    idTable.openScope();
    ast.I.decl = new VarDeclaration(ast.I, new IntTypeDenoter(dummyPos), true, ast.position);
    idTable.enter(ast.I.spelling, (Declaration) ast.I.decl);
    TypeDenoter e3Type = (TypeDenoter)ast.E3.visit(this, null);
    if(!e3Type.equals(StdEnvironment.booleanType))
      reporter.reportError("Boolean expression expected here", "", ast.E3.position);
    ast.C.visit(this, null);
    idTable.closeScope();
    return null;
  }

  // Hecho por Andrea
  @Override
  public Object visitRecDeclaration(RecDeclaration ast, Object o) {
    if (ast.PFs instanceof SequentialProcFuncs) {
      visitSequentialProcFuncsRec1((SequentialProcFuncs) ast.PFs, o);
      visitSequentialProcFuncsRec2((SequentialProcFuncs) ast.PFs, o);
    }

    if (ast.PFs instanceof ProcedureProc_Funcs || ast.PFs instanceof FunctionProc_Funcs) {
      enterSequentialProcFuncsId1(ast.PFs);
      visitSequentialProcFuncs2(ast.PFs);
    }
    
    return null;
  }

  /*
   * Metodo hecho por Deyan Sanabria desde 0
   */
  @Override
  public Object visitPrivDeclaration(PrivDeclaration ast, Object o) {
    idTable.openScope();
    ast.D1.visit(this, null);
    idTable.openScope();
    ast.D2.visit(this, null);
    idTable.privCloseScope();
    return null;
  }

  /*
   * Metodo hecho por Deyan Sanabria desde 0
   */
  @Override
  public Object visitPackageDeclaration(PackageDeclaration ast, Object o) {
    if(!idTable.createContext(ast.I.spelling)) {
      reporter.reportError ("Package \"%\" already declared",
                            ast.I.spelling, ast.I.position);
    }
    idTable.enterContext(ast.I.spelling);
    ast.D.visit(this, null);
    idTable.restoreContext();
    return null;
  }

  /*
   * Metodo hecho por Deyan Sanabria desde 0
   */
  @Override
  public Object visitSequentialPackageDeclaration(SequentialPackageDeclaration ast, Object o) {
    ast.P1.visit(this, null);
    ast.P2.visit(this, null);
    return null;
  }

  /*
   * Metodo hecho por Deyan Sanabria desde 0
   */
  @Override
  public Object visitProcedureProc_Funcs(ProcedureProc_Funcs ast, Object o) {
    idTable.enter(ast.I.spelling, ast);
    if(ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);
    idTable.openScope();
    ast.FPS.visit(this,null);
    ast.C.visit(this, null);
    idTable.closeScope();
    return null;
  }

  /*
   * Metodo hecho por Deyan Sanabria desde 0
   */
  @Override
  public Object visitFunctionProc_Funcs(FunctionProc_Funcs ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter (ast.I.spelling, ast); // permits recursion
    if (ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);
    idTable.openScope();
    ast.FPS.visit(this, null);
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    idTable.closeScope();
    if (! ast.T.equals(eType))
      reporter.reportError ("body of function \"%\" has wrong type",
                            ast.I.spelling, ast.E.position);
    return null;
  }

  
  // Se llaman en el visitRecDeclaration
  public Object visitSequentialProcFuncsRec1(SequentialProcFuncs ast, Object o) {
    // Ingresar identifiers de PF1 y PF2
    enterSequentialProcFuncsId1(ast.PF1);
    enterSequentialProcFuncsId1(ast.PF2); 
    return null;
  }

  // Segunda pasada
  public Object visitSequentialProcFuncsRec2(SequentialProcFuncs ast, Object o) {
    visitSequentialProcFuncs2(ast.PF1);
    visitSequentialProcFuncs2(ast.PF2); 
    return null;
  }

  // Primera pasada 
  private void enterSequentialProcFuncsId1(Declaration ast) {
    
    if (ast instanceof ProcedureProc_Funcs) { 
      ProcedureProc_Funcs astP = (ProcedureProc_Funcs) ast;
      idTable.enter(astP.I.spelling, astP); 
      
      if (astP.duplicated) {
        reporter.reportError("identifier \"%\" already declared",
        astP.I.spelling, astP.position);
      }

      idTable.openScope();
      astP.FPS.visit(this, null);
      idTable.closeScope();
    }
    else if (ast instanceof FunctionProc_Funcs) {
      FunctionProc_Funcs astF = (FunctionProc_Funcs) ast;
      idTable.enter(astF.I.spelling, astF); 
      
      if (astF.duplicated) {
          reporter.reportError("identifier \"%\" already declared",
                  astF.I.spelling, astF.position);
      }

      astF.T = (TypeDenoter) astF.T.visit(this, null);

      idTable.openScope();
      astF.FPS.visit(this, null);
      idTable.closeScope();
    }
    else if  (ast instanceof SequentialProcFuncs) {
      visitSequentialProcFuncsRec1((SequentialProcFuncs) ast, null);
    }
    else {
      reporter.reportError("Rec can only receive Proc or Func", "",ast.position);
    }

  }

  // Segunda pasada 
  private void visitSequentialProcFuncs2(Declaration ast) {

    if (ast instanceof SequentialProcFuncs) { 
      visitSequentialProcFuncsRec2((SequentialProcFuncs) ast, null);
    }
    else if (ast instanceof ProcedureProc_Funcs) { 
      ProcedureProc_Funcs astP = (ProcedureProc_Funcs) ast;
      idTable.openScope();
      astP.FPS.visit(this, null);
      astP.C.visit(this, null);
      idTable.closeScope();
    }
    else if (ast instanceof FunctionProc_Funcs) {
      FunctionProc_Funcs astF = (FunctionProc_Funcs) ast;
      astF.T = (TypeDenoter) astF.T.visit(this, null);
      idTable.openScope();
      astF.FPS.visit(this, null);
      TypeDenoter eType = (TypeDenoter) astF.E.visit(this, null);
      idTable.closeScope();
      if (! astF.T.equals(eType))
        reporter.reportError ("body of function \"%\" has wrong type",
      astF.I.spelling, astF.E.position);
    } else {
      reporter.reportError("Rec can only receive Proc or Func", "",ast.position);
    }

  }


  /*
   * Metodo hecho por Deyan Sanabria desde 0
   */
  @Override
  public Object visitSequentialProcFuncs(SequentialProcFuncs ast, Object o) {
    ast.PF1.visit(this, null);
    ast.PF2.visit(this, null);
    return null;
  }

  /*
   * Metodo hecho por Deyan Sanabria desde 0
   */
  @Override
  public Object visitSimpleVname(SimpleVname ast, Object o) {
    TypeDenoter varType = (TypeDenoter) ast.VAR.visit(this, null);
    ast.variable = ast.VAR.variable;
    return varType;
  }

  /*
   * Metodo hecho por Deyan Sanabria desde 0
   */
  @Override
  public Object visitCompoundVname(CompoundVname ast, Object o) {
    if(!idTable.enterContext(ast.I.spelling));
      reporter.reportError ("Package \"%\" is not declared",ast.I.spelling, ast.I.position);
    TypeDenoter varType = (TypeDenoter) ast.VAR.visit(this, null);
    ast.variable = ast.VAR.variable;
    idTable.restoreContext();
    return varType;
  }


  @Override
  public Object visitCompoundProgram(CompoundProgram ast, Object o) {
    ast.P.visit(this, null);
    ast.C.visit(this, null);
    return null;
  }


  // Commands

  // Always returns null. Does not use the given object.
  /*
   * Metodo cambiado por Deyan Sanabria:
   * Error de LHS cambiado
   */
  public Object visitAssignCommand(AssignCommand ast, Object o) {
    TypeDenoter vType = (TypeDenoter) ast.V.visit(this, null);
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    if (!ast.V.variable)
      reporter.reportError ("LHS of assignment is not an assignable variable", "", ast.V.position);
    if (! eType.equals(vType))
      reporter.reportError ("assignment incompatibilty", "", ast.position);
    return null;
  }

  /*
   * Metodo cambiado por Deyan Sanabria:
   * Se agregaron instance of ProcedureProc_Funcs
   * Se agrego errores para LongIdentifiers
   */
  public Object visitCallCommand(CallCommand ast, Object o) {
    
    Declaration binding = (Declaration) ast.LI.visit(this, null);
    if (binding == null)
      reportUndeclared(ast.LI);
    else if (binding instanceof ProcedureProc_Funcs) {
      ast.APS.visit(this, ((ProcedureProc_Funcs) binding).FPS);
    } else if (binding instanceof ProcFormalParameter) {
      ast.APS.visit(this, ((ProcFormalParameter) binding).FPS);
    } else
      if(ast.LI instanceof SimpleLongIdentifier) {
        SimpleLongIdentifier SLI = (SimpleLongIdentifier) ast.LI;
        reporter.reportError("\"%\" is not a procedure identifier",
                           SLI.I.spelling, SLI.I.position);
      } else if(ast.LI instanceof CompoundLongIdentifier) {
        CompoundLongIdentifier CLI = (CompoundLongIdentifier) ast.LI;
        reporter.reportError("\"%\" is not a procedure identifier",
                           CLI.I1.spelling + " $ " + CLI.I1.spelling, CLI.position);
      }
    return null;
  }

  /*
   * Metodo cambiado por Deyan Sanabria:
   * Se reemplazo el empty por el skip
   */
  public Object visitSkipCommand(SkipCommand ast, Object o) {
    return null;
  }

  public Object visitIfCommand(IfCommand ast, Object o) {
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    if (! eType.equals(StdEnvironment.booleanType))
      reporter.reportError("Boolean expression expected here", "", ast.E.position);
    ast.C1.visit(this, null);
    ast.C2.visit(this, null);
    return null;
  }

  public Object visitLetCommand(LetCommand ast, Object o) {
    idTable.openScope();
    ast.D.visit(this, null);
    ast.C.visit(this, null);
    idTable.closeScope();
    return null;
  }

  public Object visitSequentialCommand(SequentialCommand ast, Object o) {
    ast.C1.visit(this, null);
    ast.C2.visit(this, null);
    return null;
  }

  // Expressions

  // Returns the TypeDenoter denoting the type of the expression. Does
  // not use the given object.

  public Object visitArrayExpression(ArrayExpression ast, Object o) {
    TypeDenoter elemType = (TypeDenoter) ast.AA.visit(this, null);
    IntegerLiteral il = new IntegerLiteral(new Integer(ast.AA.elemCount).toString(),
                                           ast.position);
    ast.type = new ArrayTypeDenoter(il, elemType, ast.position);
    return ast.type;
  }

  public Object visitBinaryExpression(BinaryExpression ast, Object o) {

    TypeDenoter e1Type = (TypeDenoter) ast.E1.visit(this, null);
    TypeDenoter e2Type = (TypeDenoter) ast.E2.visit(this, null);
    Declaration binding = (Declaration) ast.O.visit(this, null);

    if (binding == null)
      reportUndeclared(ast.O);
    else {
      if (! (binding instanceof BinaryOperatorDeclaration))
        reporter.reportError ("\"%\" is not a binary operator",
                              ast.O.spelling, ast.O.position);
      BinaryOperatorDeclaration bbinding = (BinaryOperatorDeclaration) binding;
      if (bbinding.ARG1 == StdEnvironment.anyType) {
        // this operator must be "=" or "\="
        if (! e1Type.equals(e2Type))
          reporter.reportError ("incompatible argument types for \"%\"",
                                ast.O.spelling, ast.position);
      } else if (! e1Type.equals(bbinding.ARG1))
          reporter.reportError ("wrong argument type for \"%\"",
                                ast.O.spelling, ast.E1.position);
      else if (! e2Type.equals(bbinding.ARG2))
          reporter.reportError ("wrong argument type for \"%\"",
                                ast.O.spelling, ast.E2.position);
      ast.type = bbinding.RES;
    }
    return ast.type;
  }

  /*
   * Metodo cambiado por Deyan Sanabria:
   * Se agregaron instance of FunctionProc_Funcs
   * Se agrego errores para LongIdentifiers
   */
  public Object visitCallExpression(CallExpression ast, Object o) {
    Declaration binding = (Declaration) ast.LI.visit(this, null);
    if (binding == null) {
      reportUndeclared(ast.LI);
      ast.type = StdEnvironment.errorType;
    } else if (binding instanceof FunctionProc_Funcs) {
      ast.APS.visit(this, ((FunctionProc_Funcs) binding).FPS);
      ast.type = ((FunctionProc_Funcs) binding).T;
    } else if (binding instanceof FuncFormalParameter) {
      ast.APS.visit(this, ((FuncFormalParameter) binding).FPS);
      ast.type = ((FuncFormalParameter) binding).T;
    } else if(ast.LI instanceof SimpleLongIdentifier) {
      SimpleLongIdentifier SLI = (SimpleLongIdentifier) ast.LI;
      reporter.reportError("\"%\" is not a function identifier",
                        SLI.I.spelling, SLI.I.position);
    } else if(ast.LI instanceof CompoundLongIdentifier) {
      CompoundLongIdentifier CLI = (CompoundLongIdentifier) ast.LI;
      reporter.reportError("\"%\" is not a function identifier",
                        CLI.I1.spelling + " $ " + CLI.I1.spelling, CLI.position);
    }
    return ast.type;
  }

  public Object visitCharacterExpression(CharacterExpression ast, Object o) {
    ast.type = StdEnvironment.charType;
    return ast.type;
  }

  public Object visitEmptyExpression(EmptyExpression ast, Object o) {
    ast.type = null;
    return ast.type;
  }

  public Object visitIfExpression(IfExpression ast, Object o) {
    TypeDenoter e1Type = (TypeDenoter) ast.E1.visit(this, null);
    if (! e1Type.equals(StdEnvironment.booleanType))
      reporter.reportError ("Boolean expression expected here", "",
                            ast.E1.position);
    TypeDenoter e2Type = (TypeDenoter) ast.E2.visit(this, null);
    TypeDenoter e3Type = (TypeDenoter) ast.E3.visit(this, null);
    if (! e2Type.equals(e3Type))
      reporter.reportError ("incompatible limbs in if-expression", "", ast.position);
    ast.type = e2Type;
    return ast.type;
  }

  public Object visitIntegerExpression(IntegerExpression ast, Object o) {
    ast.type = StdEnvironment.integerType;
    return ast.type;
  }

  public Object visitLetExpression(LetExpression ast, Object o) {
    idTable.openScope();
    ast.D.visit(this, null);
    ast.type = (TypeDenoter) ast.E.visit(this, null);
    idTable.closeScope();
    return ast.type;
  }

  public Object visitRecordExpression(RecordExpression ast, Object o) {
    FieldTypeDenoter rType = (FieldTypeDenoter) ast.RA.visit(this, null);
    ast.type = new RecordTypeDenoter(rType, ast.position);
    return ast.type;
  }

  public Object visitUnaryExpression(UnaryExpression ast, Object o) {

    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    Declaration binding = (Declaration) ast.O.visit(this, null);
    if (binding == null) {
      reportUndeclared(ast.O);
      ast.type = StdEnvironment.errorType;
    } else if (! (binding instanceof UnaryOperatorDeclaration))
        reporter.reportError ("\"%\" is not a unary operator",
                              ast.O.spelling, ast.O.position);
    else {
      UnaryOperatorDeclaration ubinding = (UnaryOperatorDeclaration) binding;
      if (! eType.equals(ubinding.ARG))
        reporter.reportError ("wrong argument type for \"%\"",
                              ast.O.spelling, ast.O.position);
      ast.type = ubinding.RES;
    }
    return ast.type;
  }

  /*
   * Metodo hecho por Deyan Sanabria desde 0
   */
  public Object visitVnameExpression(VnameExpression ast, Object o) {
    ast.type = (TypeDenoter) ast.V.visit(this, null);
    return ast.type;
  }

  // Declarations

  // Always returns null. Does not use the given object.
  public Object visitBinaryOperatorDeclaration(BinaryOperatorDeclaration ast, Object o) {
    return null;
  }

  public Object visitConstDeclaration(ConstDeclaration ast, Object o) {
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    idTable.enter(ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitFuncDeclaration(FuncDeclaration ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter (ast.I.spelling, ast); // permits recursion
    if (ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);
    idTable.openScope();
    ast.FPS.visit(this, null);
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    idTable.closeScope();
    if (! ast.T.equals(eType))
      reporter.reportError ("body of function \"%\" has wrong type",
                            ast.I.spelling, ast.E.position);
    return null;
  }

  public Object visitProcDeclaration(ProcDeclaration ast, Object o) {
    idTable.enter (ast.I.spelling, ast); // permits recursion
    if (ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);
    idTable.openScope();
    ast.FPS.visit(this, null);
    ast.C.visit(this, null);
    idTable.closeScope();
    return null;
  }

  public Object visitSequentialDeclaration(SequentialDeclaration ast, Object o) {
    ast.D1.visit(this, null);
    ast.D2.visit(this, null);
    return null;
  }

  public Object visitTypeDeclaration(TypeDeclaration ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter (ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitUnaryOperatorDeclaration(UnaryOperatorDeclaration ast, Object o) {
    return null;
  }
  public Object visitVarDeclaration(VarDeclaration ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter (ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);

    return null;
  }


  // Array Aggregates

  // Returns the TypeDenoter for the Array Aggregate. Does not use the
  // given object.

  public Object visitMultipleArrayAggregate(MultipleArrayAggregate ast, Object o) {
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    TypeDenoter elemType = (TypeDenoter) ast.AA.visit(this, null);
    ast.elemCount = ast.AA.elemCount + 1;
    if (! eType.equals(elemType))
      reporter.reportError ("incompatible array-aggregate element", "", ast.E.position);
    return elemType;
  }

  public Object visitSingleArrayAggregate(SingleArrayAggregate ast, Object o) {
    TypeDenoter elemType = (TypeDenoter) ast.E.visit(this, null);
    ast.elemCount = 1;
    return elemType;
  }

  // Record Aggregates

  // Returns the TypeDenoter for the Record Aggregate. Does not use the
  // given object.

  public Object visitMultipleRecordAggregate(MultipleRecordAggregate ast, Object o) {
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    FieldTypeDenoter rType = (FieldTypeDenoter) ast.RA.visit(this, null);
    TypeDenoter fType = checkFieldIdentifier(rType, ast.I);
    if (fType != StdEnvironment.errorType)
      reporter.reportError ("duplicate field \"%\" in record",
                            ast.I.spelling, ast.I.position);
    ast.type = new MultipleFieldTypeDenoter(ast.I, eType, rType, ast.position);
    return ast.type;
  }

  public Object visitSingleRecordAggregate(SingleRecordAggregate ast, Object o) {
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    ast.type = new SingleFieldTypeDenoter(ast.I, eType, ast.position);
    return ast.type;
  }

  // Formal Parameters

  // Always returns null. Does not use the given object.

  public Object visitConstFormalParameter(ConstFormalParameter ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter(ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("duplicated formal parameter \"%\"",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitFuncFormalParameter(FuncFormalParameter ast, Object o) {
    idTable.openScope();
    ast.FPS.visit(this, null);
    idTable.closeScope();
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter (ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("duplicated formal parameter \"%\"",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitProcFormalParameter(ProcFormalParameter ast, Object o) {
    idTable.openScope();
    ast.FPS.visit(this, null);
    idTable.closeScope();
    idTable.enter (ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("duplicated formal parameter \"%\"",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitVarFormalParameter(VarFormalParameter ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter (ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("duplicated formal parameter \"%\"",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitEmptyFormalParameterSequence(EmptyFormalParameterSequence ast, Object o) {
    return null;
  }

  public Object visitMultipleFormalParameterSequence(MultipleFormalParameterSequence ast, Object o) {
    ast.FP.visit(this, null);
    ast.FPS.visit(this, null);
    return null;
  }

  public Object visitSingleFormalParameterSequence(SingleFormalParameterSequence ast, Object o) {
    ast.FP.visit(this, null);
    return null;
  }

  // Actual Parameters

  // Always returns null. Uses the given FormalParameter.

  public Object visitConstActualParameter(ConstActualParameter ast, Object o) {
    FormalParameter fp = (FormalParameter) o;
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);

    if(eType == null)
      return null;
    
    if (! (fp instanceof ConstFormalParameter))
      reporter.reportError ("const actual parameter not expected here", "",
                            ast.position);
    else if (! eType.equals(((ConstFormalParameter) fp).T))
      reporter.reportError ("wrong type for const actual parameter", "",
                            ast.E.position);
    return null;
  }

  /*
   * Metodo cambiado por Deyan Sanabria:
   * Se cambio FuncDeclaration por FunctionProc_Funcs
   */
  public Object visitFuncActualParameter(FuncActualParameter ast, Object o) {
    FormalParameter fp = (FormalParameter) o;

    Declaration binding = (Declaration) ast.I.visit(this, null);
    if (binding == null)
      reportUndeclared (ast.I);
    else if (! (binding instanceof FunctionProc_Funcs ||
                binding instanceof FuncFormalParameter))
      reporter.reportError ("\"%\" is not a function identifier",
                            ast.I.spelling, ast.I.position);
    else if (! (fp instanceof FuncFormalParameter))
      reporter.reportError ("func actual parameter not expected here", "",
                            ast.position);
    else {
      FormalParameterSequence FPS = null;
      TypeDenoter T = null;
      if (binding instanceof FunctionProc_Funcs) {
        FPS = ((FunctionProc_Funcs) binding).FPS;
        T = ((FunctionProc_Funcs) binding).T;
      } else {
        FPS = ((FuncFormalParameter) binding).FPS;
        T = ((FuncFormalParameter) binding).T;
      }
      if (! FPS.equals(((FuncFormalParameter) fp).FPS))
        reporter.reportError ("wrong signature for function \"%\"",
                              ast.I.spelling, ast.I.position);
      else if (! T.equals(((FuncFormalParameter) fp).T))
        reporter.reportError ("wrong type for function \"%\"",
                              ast.I.spelling, ast.I.position);
    }
    return null;
  }

  /*
   * Metodo cambiado por Deyan Sanabria:
   * Se cambio ProcDeclaration por ProcedureProc_Funcs
   */
  public Object visitProcActualParameter(ProcActualParameter ast, Object o) {
    FormalParameter fp = (FormalParameter) o;

    Declaration binding = (Declaration) ast.I.visit(this, null);
    if (binding == null)
      reportUndeclared (ast.I);
    else if (! (binding instanceof ProcedureProc_Funcs ||
                binding instanceof ProcFormalParameter))
      reporter.reportError ("\"%\" is not a procedure identifier",
                            ast.I.spelling, ast.I.position);
    else if (! (fp instanceof ProcFormalParameter))
      reporter.reportError ("proc actual parameter not expected here", "",
                            ast.position);
    else {
      FormalParameterSequence FPS = null;
      if (binding instanceof ProcedureProc_Funcs)
        FPS = ((ProcedureProc_Funcs) binding).FPS;
      else
        FPS = ((ProcFormalParameter) binding).FPS;
      if (! FPS.equals(((ProcFormalParameter) fp).FPS))
        reporter.reportError ("wrong signature for procedure \"%\"",
                              ast.I.spelling, ast.I.position);
    }
    return null;
  }

  public Object visitVarActualParameter(VarActualParameter ast, Object o) {
    FormalParameter fp = (FormalParameter) o;

    TypeDenoter vType = (TypeDenoter) ast.V.visit(this, null);
    if (! ast.V.variable)
      reporter.reportError ("actual parameter is not an assignable variable", "",
                            ast.V.position);
    else if (! (fp instanceof VarFormalParameter))
      reporter.reportError ("var actual parameter not expected here", "",
                            ast.V.position);
    else if (! vType.equals(((VarFormalParameter) fp).T))
      reporter.reportError ("wrong type for var actual parameter", "",
                            ast.V.position);
    return null;
  }

  public Object visitEmptyActualParameterSequence(EmptyActualParameterSequence ast, Object o) {
    FormalParameterSequence fps = (FormalParameterSequence) o;
    if (! (fps instanceof EmptyFormalParameterSequence))
      reporter.reportError ("too few actual parameters", "", ast.position);
    return null;
  }

  public Object visitMultipleActualParameterSequence(MultipleActualParameterSequence ast, Object o) {
    FormalParameterSequence fps = (FormalParameterSequence) o;
    if (! (fps instanceof MultipleFormalParameterSequence))
      reporter.reportError ("too many actual parameters", "", ast.position);
    else {
      ast.AP.visit(this, ((MultipleFormalParameterSequence) fps).FP);
      ast.APS.visit(this, ((MultipleFormalParameterSequence) fps).FPS);
    }
    return null;
  }

  public Object visitSingleActualParameterSequence(SingleActualParameterSequence ast, Object o) {
    FormalParameterSequence fps = (FormalParameterSequence) o;
    if (! (fps instanceof SingleFormalParameterSequence))
      reporter.reportError ("incorrect number of actual parameters", "", ast.position);
    else {
      ast.AP.visit(this, ((SingleFormalParameterSequence) fps).FP);
    }
    return null;
  }

  // Type Denoters

  // Returns the expanded version of the TypeDenoter. Does not
  // use the given object.

  public Object visitAnyTypeDenoter(AnyTypeDenoter ast, Object o) {
    return StdEnvironment.anyType;
  }

  public Object visitArrayTypeDenoter(ArrayTypeDenoter ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    if ((Integer.valueOf(ast.IL.spelling).intValue()) == 0)
      reporter.reportError ("arrays must not be empty", "", ast.IL.position);
    return ast;
  }

  public Object visitBoolTypeDenoter(BoolTypeDenoter ast, Object o) {
    return StdEnvironment.booleanType;
  }

  public Object visitCharTypeDenoter(CharTypeDenoter ast, Object o) {
    return StdEnvironment.charType;
  }

  public Object visitErrorTypeDenoter(ErrorTypeDenoter ast, Object o) {
    return StdEnvironment.errorType;
  }

  /*
   * Metodo cambiado por Deyan Sanabria:
   * Se agrego errores para LongIdentifiers
   */
  public Object visitSimpleTypeDenoter(SimpleTypeDenoter ast, Object o) {
    Declaration binding = (Declaration) ast.L.visit(this, null);
    if (binding == null) {
      reportUndeclared (ast.L);
      return StdEnvironment.errorType;
    } else if (! (binding instanceof TypeDeclaration)) {
      if(ast.L instanceof SimpleLongIdentifier) {
        SimpleLongIdentifier SLI = (SimpleLongIdentifier) ast.L;
        reporter.reportError("\"%\" is not a type identifier",
                           SLI.I.spelling, SLI.I.position);
      } else if(ast.L instanceof CompoundLongIdentifier) {
        CompoundLongIdentifier CLI = (CompoundLongIdentifier) ast.L;
        reporter.reportError("\"%\" is not a type identifier",
                           CLI.I1.spelling + " $ " + CLI.I1.spelling, CLI.position);
      }
      return StdEnvironment.errorType;
    }
    return ((TypeDeclaration) binding).T;
  }

  public Object visitIntTypeDenoter(IntTypeDenoter ast, Object o) {
    return StdEnvironment.integerType;
  }

  public Object visitRecordTypeDenoter(RecordTypeDenoter ast, Object o) {
    ast.FT = (FieldTypeDenoter) ast.FT.visit(this, null);
    return ast;
  }

  public Object visitMultipleFieldTypeDenoter(MultipleFieldTypeDenoter ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    ast.FT.visit(this, null);
    return ast;
  }

  public Object visitSingleFieldTypeDenoter(SingleFieldTypeDenoter ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    return ast;
  }

  // Literals, Identifiers and Operators
  public Object visitCharacterLiteral(CharacterLiteral CL, Object o) {
    return StdEnvironment.charType;
  }

  public Object visitIdentifier(Identifier I, Object o) {
    Declaration binding = idTable.retrieve(I.spelling);
    if (binding != null)
      I.decl = binding;
    return binding;
  }

  public Object visitIntegerLiteral(IntegerLiteral IL, Object o) {
    return StdEnvironment.integerType;
  }

  public Object visitOperator(Operator O, Object o) {
    Declaration binding = idTable.retrieve(O.spelling);
    if (binding != null)
      O.decl = binding;
    return binding;
  }

  // Value-or-variable names

  // Determines the address of a named object (constant or variable).
  // This consists of a base object, to which 0 or more field-selection
  // or array-indexing operations may be applied (if it is a record or
  // array).  As much as possible of the address computation is done at
  // compile-time. Code is generated only when necessary to evaluate
  // index expressions at run-time.
  // currentLevel is the routine level where the v-name occurs.
  // frameSize is the anticipated size of the local stack frame when
  // the object is addressed at run-time.
  // It returns the description of the base object.
  // offset is set to the total of any field offsets (plus any offsets
  // due to index expressions that happen to be literals).
  // indexed is set to true iff there are any index expressions (other
  // than literals). In that case code is generated to compute the
  // offset due to these indexing operations at run-time.

  // Returns the TypeDenoter of the Vname. Does not use the
  // given object.

  /*
   * Metodo cambiado por Deyan Sanabria:
   * Se cambio el nombre de visitDotVname al actual
   */
  public Object visitDotVarname(DotVarname ast, Object o) {
    ast.type = null;
    TypeDenoter vType = (TypeDenoter) ast.V.visit(this, null);
    ast.variable = ast.V.variable;
    if (! (vType instanceof RecordTypeDenoter))
      reporter.reportError ("record expected here", "", ast.V.position);
    else {
      ast.type = checkFieldIdentifier(((RecordTypeDenoter) vType).FT, ast.I);
      if (ast.type == StdEnvironment.errorType)
        reporter.reportError ("no field \"%\" in this record type",
                              ast.I.spelling, ast.I.position);
    }
    return ast.type;
  }

  /*
   * Metodo cambiado por Deyan Sanabria:
   * Se cambio el nombre de visitSimpleVname al actual
   */
  public Object visitSimpleVarname(SimpleVarname ast, Object o) {
    ast.variable = false;
    ast.type = StdEnvironment.errorType;
    Declaration binding = (Declaration) ast.I.visit(this, null);
    if (binding == null) {
      ast.variable = true;
      reportUndeclared(ast.I);
    }
    else
      if (binding instanceof ConstDeclaration) {
        ast.type = ((ConstDeclaration) binding).E.type;
        ast.variable = false;
      } else if (binding instanceof VarDeclaration) {
        ast.type = ((VarDeclaration) binding).T;
        if(((VarDeclaration) binding).isControl)
          ast.variable = false;
        else
          ast.variable = true;
      } else if (binding instanceof ConstFormalParameter) {
        ast.type = ((ConstFormalParameter) binding).T;
        ast.variable = false;
      } else if (binding instanceof VarFormalParameter) {
        ast.type = ((VarFormalParameter) binding).T;
        ast.variable = true;
      } else if (binding instanceof InitializedVarDeclaration) {
        ast.type = ((InitializedVarDeclaration) binding).T;
        ast.variable = true;
      } else
        reporter.reportError ("\"%\" is not a const or var identifier",
                              ast.I.spelling, ast.I.position);
    return ast.type;
  }

  /*
   * Metodo cambiado por Deyan Sanabria:
   * Se cambio el nombre de visitSubscriptVname al actual
   */
  public Object visitSubscriptVarname(SubscriptVarname ast, Object o) {
    TypeDenoter vType = (TypeDenoter) ast.V.visit(this, null);
    ast.variable = ast.V.variable;
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    if (vType != StdEnvironment.errorType) {
      if (! (vType instanceof ArrayTypeDenoter))
        reporter.reportError ("array expected here", "", ast.V.position);
      else {
        if (! eType.equals(StdEnvironment.integerType))
          reporter.reportError ("Integer expression expected here", "",
				ast.E.position);
        ast.type = ((ArrayTypeDenoter) vType).T;
      }
    }
    return ast.type;
  }

  // Programs

  // Checks whether the source program, represented by its AST, satisfies the
  // language's scope rules and type rules.
  // Also decorates the AST as follows:
  //  (a) Each applied occurrence of an identifier or operator is linked to
  //      the corresponding declaration of that identifier or operator.
  //  (b) Each expression and value-or-variable-name is decorated by its type.
  //  (c) Each type identifier is replaced by the type it denotes.
  // Types are represented by small ASTs.

  public void check(Program ast) {
    ast.visit(this, null);
  }

  /////////////////////////////////////////////////////////////////////////////

  public Checker (ErrorReporter reporter) {
    this.reporter = reporter;
    this.idTable = new ContextChangingIdTable ();
    establishStdEnvironment();
  }

  private ContextChangingIdTable idTable;
  private static SourcePosition dummyPos = new SourcePosition();
  private ErrorReporter reporter;

  // Reports that the identifier or operator used at a leaf of the AST
  // has not been declared.

  private void reportUndeclared (Terminal leaf) {
    reporter.reportError("\"%\" is not declared", leaf.spelling, leaf.position);
  }

  /*
   * Metodo hecho por Deyan Sanabria:
   */
  private void reportUndeclared (LongIdentifier leaf) {
    if(leaf instanceof SimpleLongIdentifier) {
      SimpleLongIdentifier simpleLeaf = (SimpleLongIdentifier) leaf;
      reporter.reportError("\"%\" is not declared", simpleLeaf.I.spelling, simpleLeaf.I.position);
    }else if(leaf instanceof CompoundLongIdentifier) {
      CompoundLongIdentifier compoundLeaf = (CompoundLongIdentifier) leaf;
      String compoundSpelling = compoundLeaf.I1.spelling + " $ " + compoundLeaf.I2.spelling;
      reporter.reportError("\"%\" is not declared", compoundSpelling, compoundLeaf.position);
    }
    
  }


  private static TypeDenoter checkFieldIdentifier(FieldTypeDenoter ast, Identifier I) {
    if (ast instanceof MultipleFieldTypeDenoter) {
      MultipleFieldTypeDenoter ft = (MultipleFieldTypeDenoter) ast;
      if (ft.I.spelling.compareTo(I.spelling) == 0) {
        I.decl = ast;
        return ft.T;
      } else {
        return checkFieldIdentifier (ft.FT, I);
      }
    } else if (ast instanceof SingleFieldTypeDenoter) {
      SingleFieldTypeDenoter ft = (SingleFieldTypeDenoter) ast;
      if (ft.I.spelling.compareTo(I.spelling) == 0) {
        I.decl = ast;
        return ft.T;
      }
    }
    return StdEnvironment.errorType;
  }


  // Creates a small AST to represent the "declaration" of a standard
  // type, and enters it in the identification table.

  private TypeDeclaration declareStdType (String id, TypeDenoter typedenoter) {

    TypeDeclaration binding;

    binding = new TypeDeclaration(new Identifier(id, dummyPos), typedenoter, dummyPos);
    idTable.enter(id, binding);
    return binding;
  }

  // Creates a small AST to represent the "declaration" of a standard
  // type, and enters it in the identification table.

  private ConstDeclaration declareStdConst (String id, TypeDenoter constType) {

    IntegerExpression constExpr;
    ConstDeclaration binding;

    // constExpr used only as a placeholder for constType
    constExpr = new IntegerExpression(null, dummyPos);
    constExpr.type = constType;
    binding = new ConstDeclaration(new Identifier(id, dummyPos), constExpr, dummyPos);
    idTable.enter(id, binding);
    return binding;
  }

  // Creates a small AST to represent the "declaration" of a standard
  // type, and enters it in the identification table.

  /*
   * Metodo cambiado por Deyan Sanabria:
   * Se cambio ProcDeclaration por ProcedureProc_Funcs
   */
  private ProcedureProc_Funcs declareStdProc (String id, FormalParameterSequence fps) {

    ProcedureProc_Funcs binding;

    binding = new ProcedureProc_Funcs(new Identifier(id, dummyPos), fps,
                                  new SkipCommand(dummyPos), dummyPos); // se cambio empty a skip Deyan Sanabria
                                  
    idTable.enter(id, binding);
    return binding;
  }

  // Creates a small AST to represent the "declaration" of a standard
  // type, and enters it in the identification table.

  /*
   * Metodo cambiado por Deyan Sanabria:
   * Se cambio FuncDeclaration por FunctionProc_Funcs
   */
  private FunctionProc_Funcs declareStdFunc (String id, FormalParameterSequence fps,
                                          TypeDenoter resultType) {

    FunctionProc_Funcs binding;

    binding = new FunctionProc_Funcs(new Identifier(id, dummyPos), fps, resultType,
                                  new EmptyExpression(dummyPos), dummyPos);
    idTable.enter(id, binding);
    return binding;
  }

  // Creates a small AST to represent the "declaration" of a
  // unary operator, and enters it in the identification table.
  // This "declaration" summarises the operator's type info.

  private UnaryOperatorDeclaration declareStdUnaryOp
    (String op, TypeDenoter argType, TypeDenoter resultType) {

    UnaryOperatorDeclaration binding;

    binding = new UnaryOperatorDeclaration (new Operator(op, dummyPos),
                                            argType, resultType, dummyPos);
    idTable.enter(op, binding);
    return binding;
  }

  // Creates a small AST to represent the "declaration" of a
  // binary operator, and enters it in the identification table.
  // This "declaration" summarises the operator's type info.

  private BinaryOperatorDeclaration declareStdBinaryOp
    (String op, TypeDenoter arg1Type, TypeDenoter arg2type, TypeDenoter resultType) {

    BinaryOperatorDeclaration binding;

    binding = new BinaryOperatorDeclaration (new Operator(op, dummyPos),
                                             arg1Type, arg2type, resultType, dummyPos);
    idTable.enter(op, binding);
    return binding;
  }

  // Creates small ASTs to represent the standard types.
  // Creates small ASTs to represent "declarations" of standard types,
  // constants, procedures, functions, and operators.
  // Enters these "declarations" in the identification table.

  private final static Identifier dummyI = new Identifier("", dummyPos);

  private void establishStdEnvironment () {

    // idTable.startIdentification();
    StdEnvironment.booleanType = new BoolTypeDenoter(dummyPos);
    StdEnvironment.integerType = new IntTypeDenoter(dummyPos);
    StdEnvironment.charType = new CharTypeDenoter(dummyPos);
    StdEnvironment.anyType = new AnyTypeDenoter(dummyPos);
    StdEnvironment.errorType = new ErrorTypeDenoter(dummyPos);

    StdEnvironment.booleanDecl = declareStdType("Boolean", StdEnvironment.booleanType);
    StdEnvironment.falseDecl = declareStdConst("false", StdEnvironment.booleanType);
    StdEnvironment.trueDecl = declareStdConst("true", StdEnvironment.booleanType);
    StdEnvironment.notDecl = declareStdUnaryOp("\\", StdEnvironment.booleanType, StdEnvironment.booleanType);
    StdEnvironment.andDecl = declareStdBinaryOp("/\\", StdEnvironment.booleanType, StdEnvironment.booleanType, StdEnvironment.booleanType);
    StdEnvironment.orDecl = declareStdBinaryOp("\\/", StdEnvironment.booleanType, StdEnvironment.booleanType, StdEnvironment.booleanType);

    StdEnvironment.integerDecl = declareStdType("Integer", StdEnvironment.integerType);
    StdEnvironment.maxintDecl = declareStdConst("maxint", StdEnvironment.integerType);
    StdEnvironment.addDecl = declareStdBinaryOp("+", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
    StdEnvironment.subtractDecl = declareStdBinaryOp("-", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
    StdEnvironment.multiplyDecl = declareStdBinaryOp("*", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
    StdEnvironment.divideDecl = declareStdBinaryOp("/", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
    StdEnvironment.moduloDecl = declareStdBinaryOp("//", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
    StdEnvironment.lessDecl = declareStdBinaryOp("<", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);
    StdEnvironment.notgreaterDecl = declareStdBinaryOp("<=", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);
    StdEnvironment.greaterDecl = declareStdBinaryOp(">", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);
    StdEnvironment.notlessDecl = declareStdBinaryOp(">=", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);

    StdEnvironment.charDecl = declareStdType("Char", StdEnvironment.charType);
    StdEnvironment.chrDecl = declareStdFunc("chr", new SingleFormalParameterSequence(
                                      new ConstFormalParameter(dummyI, StdEnvironment.integerType, dummyPos), dummyPos), StdEnvironment.charType);
    StdEnvironment.ordDecl = declareStdFunc("ord", new SingleFormalParameterSequence(
                                      new ConstFormalParameter(dummyI, StdEnvironment.charType, dummyPos), dummyPos), StdEnvironment.integerType);
    StdEnvironment.eofDecl = declareStdFunc("eof", new EmptyFormalParameterSequence(dummyPos), StdEnvironment.booleanType);
    StdEnvironment.eolDecl = declareStdFunc("eol", new EmptyFormalParameterSequence(dummyPos), StdEnvironment.booleanType);
    StdEnvironment.getDecl = declareStdProc("get", new SingleFormalParameterSequence(
                                      new VarFormalParameter(dummyI, StdEnvironment.charType, dummyPos), dummyPos));
    StdEnvironment.putDecl = declareStdProc("put", new SingleFormalParameterSequence(
                                      new ConstFormalParameter(dummyI, StdEnvironment.charType, dummyPos), dummyPos));
    StdEnvironment.getintDecl = declareStdProc("getint", new SingleFormalParameterSequence(
                                            new VarFormalParameter(dummyI, StdEnvironment.integerType, dummyPos), dummyPos));
    StdEnvironment.putintDecl = declareStdProc("putint", new SingleFormalParameterSequence(
                                            new ConstFormalParameter(dummyI, StdEnvironment.integerType, dummyPos), dummyPos));
    StdEnvironment.geteolDecl = declareStdProc("geteol", new EmptyFormalParameterSequence(dummyPos));
    StdEnvironment.puteolDecl = declareStdProc("puteol", new EmptyFormalParameterSequence(dummyPos));
    StdEnvironment.equalDecl = declareStdBinaryOp("=", StdEnvironment.anyType, StdEnvironment.anyType, StdEnvironment.booleanType);
    StdEnvironment.unequalDecl = declareStdBinaryOp("\\=", StdEnvironment.anyType, StdEnvironment.anyType, StdEnvironment.booleanType);
    idTable.saveStdEnvironmentLatestEntry();

  }

}
