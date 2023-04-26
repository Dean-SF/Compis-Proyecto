/*
 * Andrea
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

public class CompoundProgram extends Program {

  public CompoundProgram (Package pAST, Command cAST, SourcePosition thePosition) {
    super (thePosition);
    P = pAST;
    C = cAST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitCompoundProgram(this, o);
  }

  public Package P;
  public Command C;
}