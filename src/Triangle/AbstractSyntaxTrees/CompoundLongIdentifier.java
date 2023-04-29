/*
 * @(#)Command.java                        2.1 2003/10/07
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

package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;
// Hecho por Deyan
public class CompoundLongIdentifier extends LongIdentifier{

  public CompoundLongIdentifier (Identifier i1AST, Identifier i2AST, SourcePosition thePosition) {
    super (thePosition);
    I1 = i1AST;
    I2 = i2AST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitCompoundLongIdentifier(this, o);
  }

  public Identifier I1,I2;
}
