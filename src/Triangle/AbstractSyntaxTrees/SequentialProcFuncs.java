/*
 * Ericka
 */

package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;


public class SequentialProcFuncs extends Proc_Funcs {
    public SequentialProcFuncs (Proc_Funcs pf1AST, Proc_Funcs pf2AST, SourcePosition thePosition) {
        super (thePosition);
        PF1 = pf1AST;
        PF2 = pf2AST;
      }
    
      public Object visit(Visitor v, Object o) {
        return v.visitSequentialProcFuncs(this, o);
      }
    
      public Proc_Funcs PF1, PF2;
}
