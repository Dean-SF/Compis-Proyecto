package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

public class RepeatWhileCommand extends Command {
    public RepeatWhileCommand(Expression eAST, Command cAST,
                              SourcePosition thePosition) {
        super (thePosition);
        E = eAST;
        C = cAST;
    }

    public Object visit(Visitor v, Object o) {
        return v.visitRepeatWhileCommand(this, o);
    }

    public Expression E;
    public Command C;
}