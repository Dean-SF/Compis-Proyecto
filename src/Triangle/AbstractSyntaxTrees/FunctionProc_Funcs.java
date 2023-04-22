/*
 * Ericka
 */

package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;


public class FunctionProc_Funcs extends Proc_Funcs{
    public FunctionProc_Funcs (Identifier iAST, FormalParameterSequence fpsAST, TypeDenoter tAST, Expression eAST,
                    SourcePosition thePosition) {
    super (thePosition);
    I = iAST;
    FPS = fpsAST;
    T = tAST;
    E = eAST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitFunctionProc_Funcs(this, o);
  }

  public Identifier I;
  public FormalParameterSequence FPS;
  public TypeDenoter T;
  public Expression E;

}
