package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;
// Hecho por Deyan
public class RepeatDoWhileCommand extends Command {
    public RepeatDoWhileCommand(Expression eAST, Command cAST,
                              SourcePosition thePosition) {
        super (thePosition);
        C = cAST;
        E = eAST;   
    }

    public Object visit(Visitor v, Object o) {
        return v.visitRepeatDoWhileCommand(this, o);
    }

    public Expression E;
    public Command C;
}