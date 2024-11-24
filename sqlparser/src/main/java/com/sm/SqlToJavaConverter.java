package com.sm;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;


public class SqlToJavaConverter {
    public static void main(String[] args) throws Exception {
        String sql = "SELECT * FROM users WHERE age > 30 AND name = 'Alice'";

        // Parse the SQL query
        Select select = (Select) CCJSqlParserUtil.parse(sql);
        Expression whereExpression = select.getPlainSelect().getWhere();

        // Convert WHERE clause to Java function
        String javaComparisonFunction = convertWhereClauseToJava(whereExpression);
        System.out.println(javaComparisonFunction);

        int age = 35;
        String name = "'Alice'";

        javaComparisonFunction = javaComparisonFunction.replace("age", String.valueOf(age))
            .replace("name", name);

        System.out.println(checkCondition(javaComparisonFunction));
    }

    private static boolean checkCondition(String ifCondition) throws Exception{
        return new DynamicComparisionEvaluator(ifCondition).evaluate();
    }

    private static String convertWhereClauseToJava(Expression expression) {
        StringBuilder javaCode = new StringBuilder();

        ExpressionVisitorAdapter<Void> expressionVisitorAdapter = new ExpressionVisitorAdapter<>() {
            
            @Override
            public <S> Void visit(GreaterThan greaterThan, S context) {
                javaCode.append("(")
                    .append(greaterThan.getLeftExpression())
                    .append(" > ")
                    .append(greaterThan.getRightExpression())
                    .append(")");
                return null;
            }

            @Override
            public <S> Void visit(EqualsTo equalsTo, S context) {
                javaCode.append("(")
                    .append(equalsTo.getLeftExpression())
                    .append(" == ")
                    .append(equalsTo.getRightExpression())
                    .append(")");
                return null;
            }

            @Override
            public <S> Void visit(LikeExpression likeExpression, S context) {
                javaCode.append("(")
                    .append(likeExpression.getLeftExpression())
                    .append(".contains(")
                    .append(likeExpression.getRightExpression())
                    .append("))");
                return null;
            }

            @Override
            public <S> Void visit(AndExpression andExpression, S context) {
                javaCode.append("(");
                andExpression.getLeftExpression().accept(this);
                javaCode.append(" && ");
                andExpression.getRightExpression().accept(this);
                javaCode.append(")");
                return null;
            }

            @Override
            public <S> Void visit(OrExpression orExpression, S context) {
                javaCode.append("(");
                orExpression.getLeftExpression().accept(this);
                javaCode.append(" || ");
                orExpression.getRightExpression().accept(this);
                javaCode.append(")");
                return null;
            }

            @Override
            public <S> Void visit(Column column, S context) {
                System.out.println("Found a Column " + column.getColumnName());
                return null;
            }
        };

        expression.accept(expressionVisitorAdapter);
        return javaCode.toString();
    }
}
