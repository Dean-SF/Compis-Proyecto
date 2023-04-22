/*
 * Ericka
 */

package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;


public class ProcedureProc_Funcs extends Proc_Funcs{
    public ProcedureProc_Funcs (Identifier iAST, FormalParameterSequence fpsAST, Command cAST,
                    SourcePosition thePosition) {
    super (thePosition);
    I = iAST;
    FPS = fpsAST;
    C = cAST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitProcedureProc_Funcs(this, o);
  }

  public Identifier I;
  public FormalParameterSequence FPS;
  public Command C;
}
