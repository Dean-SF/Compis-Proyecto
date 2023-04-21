package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

public class RepeatTimesCommand extends Command {
    public RepeatTimesCommand(Expression eAST, Command cAST,
                              SourcePosition thePosition) {
        super (thePosition);
        E = eAST;
        C = cAST;
    }

    public Object visit(Visitor v, Object o) {
        return v.visitRepeatTimesCommand(this, o);
    }

    public Expression E;
    public Command C;
}