package Triangle.Writers;

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
import Triangle.AbstractSyntaxTrees.CompoundVname;
import Triangle.AbstractSyntaxTrees.ConstActualParameter;
import Triangle.AbstractSyntaxTrees.ConstDeclaration;
import Triangle.AbstractSyntaxTrees.ConstFormalParameter;
import Triangle.AbstractSyntaxTrees.DotVarname;
import Triangle.AbstractSyntaxTrees.EmptyActualParameterSequence;
import Triangle.AbstractSyntaxTrees.SkipCommand;
import Triangle.AbstractSyntaxTrees.EmptyExpression;
import Triangle.AbstractSyntaxTrees.EmptyFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.ErrorTypeDenoter;
import Triangle.AbstractSyntaxTrees.ForCommand;
import Triangle.AbstractSyntaxTrees.ForUntilCommand;
import Triangle.AbstractSyntaxTrees.ForWhileCommand;
import Triangle.AbstractSyntaxTrees.FuncActualParameter;
import Triangle.AbstractSyntaxTrees.FuncDeclaration;
import Triangle.AbstractSyntaxTrees.FuncFormalParameter;
import Triangle.AbstractSyntaxTrees.FunctionProc_Funcs;
import Triangle.AbstractSyntaxTrees.Identifier;
import Triangle.AbstractSyntaxTrees.IfCommand;
import Triangle.AbstractSyntaxTrees.IfExpression;
import Triangle.AbstractSyntaxTrees.IntTypeDenoter;
import Triangle.AbstractSyntaxTrees.IntegerExpression;
import Triangle.AbstractSyntaxTrees.IntegerLiteral;
import Triangle.AbstractSyntaxTrees.LetCommand;
import Triangle.AbstractSyntaxTrees.LetExpression;
import Triangle.AbstractSyntaxTrees.MultipleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleArrayAggregate;
import Triangle.AbstractSyntaxTrees.MultipleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.MultipleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleRecordAggregate;
import Triangle.AbstractSyntaxTrees.Operator;
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
import Triangle.AbstractSyntaxTrees.SequentialProcFuncs;
import Triangle.AbstractSyntaxTrees.SimpleLongIdentifier;
import Triangle.AbstractSyntaxTrees.SimpleTypeDenoter;
import Triangle.AbstractSyntaxTrees.SimpleVarname;
import Triangle.AbstractSyntaxTrees.SimpleVname;
import Triangle.AbstractSyntaxTrees.SingleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleArrayAggregate;
import Triangle.AbstractSyntaxTrees.SingleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.SingleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleRecordAggregate;
import Triangle.AbstractSyntaxTrees.SubscriptVarname;
import Triangle.AbstractSyntaxTrees.TypeDeclaration;
import Triangle.AbstractSyntaxTrees.UnaryExpression;
import Triangle.AbstractSyntaxTrees.UnaryOperatorDeclaration;
import Triangle.AbstractSyntaxTrees.VarActualParameter;
import Triangle.AbstractSyntaxTrees.VarDeclaration;
import Triangle.AbstractSyntaxTrees.VarFormalParameter;
import Triangle.AbstractSyntaxTrees.Visitor;
import Triangle.AbstractSyntaxTrees.VnameExpression;
import java.io.FileWriter;
import java.io.IOException;

public class WriterXMLVisitor implements Visitor {

    private FileWriter fileWriter;

    WriterXMLVisitor(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    //Long-Identifiers
    public Object visitCompoundLongIdentifier(CompoundLongIdentifier ast, Object obj) {
        writeLineXML("<CompoundLongIdentifier>");
        ast.I1.visit(this, null);
        ast.I2.visit(this, null);
        writeLineXML("</CompoundLongIdentifier>");
        return null;
    }

    public Object visitSimpleLongIdentifier(SimpleLongIdentifier ast, Object obj) {
        writeLineXML("<SimpleLongIdentifier>");
        ast.I.visit(this, null);
        writeLineXML("</SimpleLongIdentifier>");
        return null;
    }

    // Commands
    public Object visitAssignCommand(AssignCommand ast, Object obj) {
        writeLineXML("<AssignCommand>");
        ast.V.visit(this, null);
        ast.E.visit(this, null);
        writeLineXML("</AssignCommand>");
        return null;
    }

    public Object visitCallCommand(CallCommand ast, Object obj) {
        writeLineXML("<CallCommand>");
        ast.LI.visit(this, null);
        ast.APS.visit(this, null);
        writeLineXML("</CallCommand>");
        return null;
    }

    public Object visitSkipCommand(SkipCommand ast, Object obj) {
        writeLineXML("<SkipCommand/>");
        return null;
    }

    public Object visitIfCommand(IfCommand ast, Object obj) {
        writeLineXML("<IfCommand>");
        ast.E.visit(this, null);
        ast.C1.visit(this, null);
        ast.C2.visit(this, null);
        writeLineXML("</IfCommand>");
        return null;
    }

    public Object visitLetCommand(LetCommand ast, Object obj) {
        writeLineXML("<LetCommand>");
        ast.D.visit(this, null);
        ast.C.visit(this, null);
        writeLineXML("</LetCommand>");
        return null;
    }

    public Object visitSequentialCommand(SequentialCommand ast, Object obj) {
        writeLineXML("<SequentialCommand>");
        ast.C1.visit(this, null);
        ast.C2.visit(this, null);
        writeLineXML("</SequentialCommand>");
        return null;
    }

    public Object visitRepeatTimesCommand(RepeatTimesCommand ast, Object obj) {
        writeLineXML("<RepeatTimesCommand>");
        ast.E.visit(this, null);
        ast.C.visit(this, null);
        writeLineXML("</RepeatTimesCommand>");
        return null;
    }

    public Object visitRepeatWhileCommand(RepeatWhileCommand ast, Object obj) {
        writeLineXML("<RepeatWhileCommand>");
        ast.E.visit(this, null);
        ast.C.visit(this, null);
        writeLineXML("</RepeatWhileCommand>");
        return null;
    }

    public Object visitRepeatDoWhileCommand(RepeatDoWhileCommand ast, Object obj) {
        writeLineXML("<RepeatDoWhileCommand>");
        ast.C.visit(this, null);
        ast.E.visit(this, null);
        writeLineXML("</RepeatDoWhileCommand>");
        return null;
    }

    public Object visitForCommand(ForCommand ast, Object obj) {
        writeLineXML("<ForCommand>");
        ast.I.visit(this,null);
        ast.E1.visit(this,null);
        ast.E2.visit(this,null);
        ast.C.visit(this,null);
        writeLineXML("</ForCommand>");
        return null;
    }

    public Object visitRepeatUntilCommand(RepeatUntilCommand ast, Object obj) {
        writeLineXML("<RepeatUntilCommand>");
        ast.E.visit(this, null);
        ast.C.visit(this, null);
        writeLineXML("</RepeatUntilCommand>");
        return null;
    }

    public Object visitRepeatDoUntilCommand(RepeatUntilCommand ast, Object obj) {
        writeLineXML("<RepeatDoUntilCommand>");
        ast.C.visit(this, null);
        ast.E.visit(this, null);
        writeLineXML("</RepeatDoUntilCommand>");
        return null;
    }

    public Object visitRepeatDoUntilCommand(RepeatDoUntilCommand ast, Object obj) {
        writeLineXML("<RepeatDoUntilCommand>");
        ast.E.visit(this, null);
        ast.C.visit(this, null);
        writeLineXML("</RepeatDoUntilCommand>");
        return null;
    }

    public Object visitForWhileCommand(ForWhileCommand ast, Object obj) {
        writeLineXML("<ForWhileCommand>");
        ast.I.visit(this,null);
        ast.E1.visit(this,null);
        ast.E2.visit(this,null);
        ast.E3.visit(this,null);
        ast.C.visit(this,null);
        writeLineXML("</ForWhileCommand>");
        return null;
    }

    public Object visitForUntilCommand(ForUntilCommand ast, Object obj) {
        writeLineXML("<ForUntilCommand>");
        ast.I.visit(this,null);
        ast.E1.visit(this,null);
        ast.E2.visit(this,null);
        ast.E3.visit(this,null);
        ast.C.visit(this,null);
        writeLineXML("</ForUntilCommand>");
        return null;
    }

    // Expressions
    public Object visitArrayExpression(ArrayExpression ast, Object obj) {
        writeLineXML("<ArrayExpression>");
        ast.AA.visit(this, null);
        writeLineXML("</ArrayExpression>");
        return null;
    }

    public Object visitBinaryExpression(BinaryExpression ast, Object obj) {
        writeLineXML("<BinaryExpression>");
        ast.E1.visit(this, null);
        ast.O.visit(this, null);
        ast.E2.visit(this, null);
        writeLineXML("</BinaryExpression>");
        return null;
    }

    public Object visitCallExpression(CallExpression ast, Object obj) {
        writeLineXML("<CallExpression>");
        ast.LI.visit(this, null);
        ast.APS.visit(this, null);
        writeLineXML("</CallExpression>");
        return null;
    }

    public Object visitCharacterExpression(CharacterExpression ast, Object obj) {
        writeLineXML("<CharacterExpression>");
        ast.CL.visit(this, null);
        writeLineXML("</CharacterExpression>");
        return null;
    }

    public Object visitEmptyExpression(EmptyExpression ast, Object obj) {
        writeLineXML("<EmptyExpression/>");
        return null;
    }

    public Object visitIfExpression(IfExpression ast, Object obj) {
        writeLineXML("<IfExpression>");
        ast.E1.visit(this, null);
        ast.E2.visit(this, null);
        ast.E3.visit(this, null);
        writeLineXML("</IfExpression>");
        return null;
    }

    public Object visitIntegerExpression(IntegerExpression ast, Object obj) {
        writeLineXML("<IntegerExpression>");
        ast.IL.visit(this, null);
        writeLineXML("</IntegerExpression>");
        return null;
    }

    public Object visitLetExpression(LetExpression ast, Object obj) {
        writeLineXML("<LetExpression>");
        ast.D.visit(this, null);
        ast.E.visit(this, null);
        writeLineXML("</LetExpression>");
        return null;
    }

    public Object visitRecordExpression(RecordExpression ast, Object obj) {
        writeLineXML("<RecordExpression>");
        ast.RA.visit(this, null);
        writeLineXML("</RecordExpression>");
        return null;
    }

    public Object visitUnaryExpression(UnaryExpression ast, Object obj) {
        writeLineXML("<UnaryExpression>");
        ast.O.visit(this, null);
        ast.E.visit(this, null);
        writeLineXML("</UnaryExpression>");
        return null;
    }

    public Object visitVnameExpression(VnameExpression ast, Object obj) {
        writeLineXML("<VnameExpression>");
        ast.V.visit(this, null);
        writeLineXML("</VnameExpression>");
        return null;
    }


    // Declarations
    public Object visitBinaryOperatorDeclaration(BinaryOperatorDeclaration ast, Object obj) {
        writeLineXML("<BinaryOperatorDeclaration>");
        ast.O.visit(this, null);
        ast.ARG1.visit(this, null);
        ast.ARG2.visit(this, null);
        ast.RES.visit(this, null);
        writeLineXML("</BinaryOperatorDeclaration>");
        return null;
    }

    public Object visitConstDeclaration(ConstDeclaration ast, Object obj) {
        writeLineXML("<ConstDeclaration>");
        ast.I.visit(this, null);
        ast.E.visit(this, null);
        writeLineXML("</ConstDeclaration>");
        return null;
    }

    public Object visitFuncDeclaration(FuncDeclaration ast, Object obj) {
        writeLineXML("<FuncDeclaration>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        ast.T.visit(this, null);
        ast.E.visit(this, null);
        writeLineXML("</FuncDeclaration>");
        return null;
    }

    public Object visitProcDeclaration(ProcDeclaration ast, Object obj) {
        writeLineXML("<ProcDeclaration>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        ast.C.visit(this, null);
        writeLineXML("</ProcDeclaration>");
        return null;
    }

    public Object visitSequentialDeclaration(SequentialDeclaration ast, Object obj) {
        writeLineXML("<SequentialDeclaration>");
        ast.D1.visit(this, null);
        ast.D2.visit(this, null);
        writeLineXML("</SequentialDeclaration>");
        return null;
    }

    public Object visitTypeDeclaration(TypeDeclaration ast, Object obj) {
        writeLineXML("<TypeDeclaration>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        writeLineXML("</TypeDeclaration>");
        return null;
    }

    public Object visitUnaryOperatorDeclaration(UnaryOperatorDeclaration ast, Object obj) {
        writeLineXML("<UnaryOperatorDeclaration>");
        ast.O.visit(this, null);
        ast.ARG.visit(this, null);
        ast.RES.visit(this, null);
        writeLineXML("</UnaryOperatorDeclaration>");
        return null;
    }

    public Object visitVarDeclaration(VarDeclaration ast, Object obj) {
        writeLineXML("<VarDeclaration>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        writeLineXML("</VarDeclaration>");
        return null;
    }

    public Object visitRecDeclaration(RecDeclaration ast, Object obj) {
        writeLineXML("<RecDeclaration>");
        ast.PFs.visit(this, null);
        writeLineXML("</RecDeclaration>");
        return null;
    }

    public Object visitPrivDeclaration(PrivDeclaration ast, Object obj) {
        writeLineXML("<PrivDeclaration>");
        ast.D1.visit(this, null);
        ast.D2.visit(this, null);
        writeLineXML("</PrivDeclaration>");
        return null;
    }

    //Proc_Funcs
    public Object visitProcedureProc_Funcs(ProcedureProc_Funcs ast, Object obj) {
        writeLineXML("<ProcedureProcFuncs>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        ast.C.visit(this, null);
        writeLineXML("</ProcedureProcFuncs>");
        return null;
    }

    public Object visitFunctionProc_Funcs(FunctionProc_Funcs ast, Object obj) {
        writeLineXML("<FunctionProcFuncs>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        ast.T.visit(this, null);
        ast.E.visit(this, null);
        writeLineXML("</FunctionProcFuncs>");
        return null;
    }

    public Object visitSequentialProcFuncs(SequentialProcFuncs ast, Object obj) {
        writeLineXML("<SequentialProcFuncs>");
        ast.PF1.visit(this, null);
        ast.PF2.visit(this, null);
        writeLineXML("</SequentialProcFuncs>");
        return null;
    }

    // Array Aggregates
    public Object visitMultipleArrayAggregate(MultipleArrayAggregate ast, Object obj) {
        writeLineXML("<MultipleArrayAggregate>");
        ast.E.visit(this, null);
        ast.AA.visit(this, null);
        writeLineXML("</MultipleArrayAggregate>");
        return null;
    }

    public Object visitSingleArrayAggregate(SingleArrayAggregate ast, Object obj) {
        writeLineXML("<SingleArrayAggregate>");
        ast.E.visit(this, null);
        writeLineXML("</SingleArrayAggregate>");
        return null;
    }


    // Record Aggregates
    public Object visitMultipleRecordAggregate(MultipleRecordAggregate ast, Object obj) {
        writeLineXML("<MultipleRecordAggregate>");
        ast.I.visit(this, null);
        ast.E.visit(this, null);
        ast.RA.visit(this, null);
        writeLineXML("</MultipleRecordAggregate>");
        return null;
    }

    public Object visitSingleRecordAggregate(SingleRecordAggregate ast, Object obj) {
        writeLineXML("<SingleRecordAggregate>");
        ast.I.visit(this, null);
        ast.E.visit(this, null);
        writeLineXML("</SingleRecordAggregate>");
        return null;
    }


    // Formal Parameters
    public Object visitConstFormalParameter(ConstFormalParameter ast, Object obj) {
        writeLineXML("<ConstFormalParameter>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        writeLineXML("</ConstFormalParameter>");
        return null;
    }

    public Object visitFuncFormalParameter(FuncFormalParameter ast, Object obj) {
        writeLineXML("<FuncFormalParameter>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        ast.T.visit(this, null);
        writeLineXML("<FuncFormalParameter>");
        return null;
    }

    public Object visitProcFormalParameter(ProcFormalParameter ast, Object obj) {
        writeLineXML("<ProcFormalParameter>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        writeLineXML("</ProcFormalParameter>");
        return null;
    }

    public Object visitVarFormalParameter(VarFormalParameter ast, Object obj) {
        writeLineXML("<VarFormalParameter>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        writeLineXML("</VarFormalParameter>");
        return null;
    }


    public Object visitEmptyFormalParameterSequence(EmptyFormalParameterSequence ast, Object obj) {
        writeLineXML("<EmptyFormalParameterSequence/>");
        return null;
    }

    public Object visitMultipleFormalParameterSequence(MultipleFormalParameterSequence ast, Object obj) {
        writeLineXML("<MultipleFormalParameterSequence>");
        ast.FP.visit(this, null);
        ast.FPS.visit(this, null);
        writeLineXML("</MultipleFormalParameterSequence>");
        return null;
    }

    public Object visitSingleFormalParameterSequence(SingleFormalParameterSequence ast, Object obj) {
        writeLineXML("<SingleFormalParameterSequence>");
        ast.FP.visit(this, null);
        writeLineXML("</SingleFormalParameterSequence>");
        return null;
    }


    // Actual Parameters
    public Object visitConstActualParameter(ConstActualParameter ast, Object obj) {
        writeLineXML("<ConstActualParameter>");
        ast.E.visit(this, null);
        writeLineXML("</ConstActualParameter>");
        return null;
    }

    public Object visitFuncActualParameter(FuncActualParameter ast, Object obj) {
        writeLineXML("<FuncActualParameter>");
        ast.I.visit(this, null);
        writeLineXML("</FuncActualParameter>");
        return null;
    }

    public Object visitProcActualParameter(ProcActualParameter ast, Object obj) {
        writeLineXML("<ProcActualParameter>");
        ast.I.visit(this, null);
        writeLineXML("</ProcActualParameter>");
        return null;
    }

    public Object visitVarActualParameter(VarActualParameter ast, Object obj) {
        writeLineXML("<VarActualParameter>");
        ast.V.visit(this, null);
        writeLineXML("</VarActualParameter>");
        return null;
    }


    public Object visitEmptyActualParameterSequence(EmptyActualParameterSequence ast, Object obj) {
        writeLineXML("<EmptyActualParameterSequence/>");
        return null;
    }

    public Object visitMultipleActualParameterSequence(MultipleActualParameterSequence ast, Object obj) {
        writeLineXML("<MultipleActualParameterSequence>");
        ast.AP.visit(this, null);
        ast.APS.visit(this, null);
        writeLineXML("</MultipleActualParameterSequence>");
        return null;
    }

    public Object visitSingleActualParameterSequence(SingleActualParameterSequence ast, Object obj) {
        writeLineXML("<SingleActualParameterSequence>");
        ast.AP.visit(this, null);
        writeLineXML("</SingleActualParameterSequence>");
        return null;
    }


    // Type Denoters
    public Object visitAnyTypeDenoter(AnyTypeDenoter ast, Object obj) {
        writeLineXML("<AnyTypeDenoter/>");
        return null;
    }

    public Object visitArrayTypeDenoter(ArrayTypeDenoter ast, Object obj) {
        writeLineXML("<ArrayTypeDenoter>");
        ast.IL.visit(this, null);
        ast.T.visit(this, null);
        writeLineXML("</ArrayTypeDenoter>");
        return null;
    }

    public Object visitBoolTypeDenoter(BoolTypeDenoter ast, Object obj) {
        writeLineXML("<BoolTypeDenoter/>");
        return null;
    }

    public Object visitCharTypeDenoter(CharTypeDenoter ast, Object obj) {
        writeLineXML("<CharTypeDenoter/>");
        return null;
    }

    public Object visitErrorTypeDenoter(ErrorTypeDenoter ast, Object obj) {
        writeLineXML("<ErrorTypeDenoter/>");
        return null;
    }

    public Object visitSimpleTypeDenoter(SimpleTypeDenoter ast, Object obj) {
        writeLineXML("<SimpleTypeDenoter>");
        ast.I.visit(this, null);
        writeLineXML("</SimpleTypeDenoter>");
        return null;
    }

    public Object visitIntTypeDenoter(IntTypeDenoter ast, Object obj) {
        writeLineXML("<IntTypeDenoter/>");
        return null;
    }

    public Object visitRecordTypeDenoter(RecordTypeDenoter ast, Object obj) {
        writeLineXML("<RecordTypeDenoter>");
        ast.FT.visit(this, null);
        writeLineXML("</RecordTypeDenoter>");
        return null;
    }


    public Object visitMultipleFieldTypeDenoter(MultipleFieldTypeDenoter ast, Object obj) {
        writeLineXML("<MultipleFieldTypeDenoter>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        ast.FT.visit(this, null);
        writeLineXML("</MultipleFieldTypeDenoter>");
        return null;
    }

    public Object visitSingleFieldTypeDenoter(SingleFieldTypeDenoter ast, Object obj) {
        writeLineXML("<SingleFieldTypeDenoter>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        writeLineXML("</SingleFieldTypeDenoter>");
        return null;
    }


    // Literals, Identifiers and Operators
    public Object visitCharacterLiteral(CharacterLiteral ast, Object obj) {
        writeLineXML("<CharacterLiteral value=\"" + ast.spelling + "\"/>");
        return null;
    }

    public Object visitIdentifier(Identifier ast, Object obj) {
        writeLineXML("<Identifier value=\"" + ast.spelling + "\"/>");
        return null;
    }

    public Object visitIntegerLiteral(IntegerLiteral ast, Object obj) {
        writeLineXML("<IntegerLiteral value=\"" + ast.spelling + "\"/>");
        return null;
    }

    public Object visitOperator(Operator ast, Object obj) {
        writeLineXML("<Operator value=\"" + transformOperator(ast.spelling) + "\"/>");
        return null;
    }


    // Value-or-variable names
    public Object visitDotVarname(DotVarname ast, Object obj) {
        writeLineXML("<DotVarname>");
        ast.V.visit(this, null);
        ast.I.visit(this, null);
        writeLineXML("</DotVarname>");
        return null;
    }

    public Object visitSimpleVarname(SimpleVarname ast, Object obj) {
        writeLineXML("<SimpleVarname>");
        ast.I.visit(this, null);
        writeLineXML("</SimpleVarname>");
        return null;
    }

    public Object visitSubscriptVarname(SubscriptVarname ast, Object obj) {
        writeLineXML("<SubscriptVarname>");
        ast.V.visit(this, null);
        ast.E.visit(this, null);
        writeLineXML("</SubscriptVarname>");
        return null;
    }

    public Object visitSimpleVname(SimpleVname ast, Object obj) {
        writeLineXML("<SimpleVname>");
        ast.VAR.visit(this, null);
        writeLineXML("</SimpleVname>");
        return null;
    }

    public Object visitCompoundVname(CompoundVname ast, Object obj) {
        writeLineXML("<CompoundVname>");
        ast.I.visit(this, null);
        ast.VAR.visit(this, null);
        writeLineXML("</CompoundVname>");
        return null;
    }


    // Programs
    public Object visitProgram(Program ast, Object obj) {
        writeLineXML("<Program>");
        ast.C.visit(this, null);
        writeLineXML("</Program>");
        return null;
    }

    private void writeLineXML(String line) {
        try {
            fileWriter.write(line);
            fileWriter.write('\n');
        } catch (IOException e) {
            System.err.println("Error while writing file for print the AST");
            e.printStackTrace();
        }
    }

    /*
     * Convert the characters "<" & "<=" to their equivalents in XML
     */
    private String transformOperator(String operator) {
        if (operator.compareTo("<") == 0)
            return "&lt;";
        else if (operator.compareTo("<=") == 0)
            return "&lt;=";
        else
            return operator;
    }

}