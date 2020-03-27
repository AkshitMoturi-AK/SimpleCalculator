package com.example.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultReference;
    float firstOperand = 0;
    float secondOperand = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnZero = (Button)findViewById(R.id.zero);
        resultReference = (TextView)findViewById(R.id.ResultTextView);
        btnZero.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.zero : changeResult("0");
                break;
            case R.id.doubleZero : changeResult("00");
                break;
            case R.id.one : changeResult("1");
                break;
            case R.id.two : changeResult("2");
                break;
            case R.id.three : changeResult("3");
                break;
            case R.id.four : changeResult("4");
                break;
            case R.id.five : changeResult("5");
                break;
            case R.id.six : changeResult("6");
                break;
            case R.id.seven : changeResult("7");
                break;
            case R.id.eight : changeResult("8");
                break;
            case R.id.nine : changeResult("9");
                break;
            case R.id.dot : resultReference.setText(resultReference.getText().toString()+".");
                break;
            case R.id.add : resultReference.setText(resultReference.getText().toString()+"+");
                break;
            case R.id.mul : resultReference.setText(resultReference.getText().toString()+"*");
                break;
            case R.id.equals : calculateResult();
                break;




        }
    }

    private void calculateResult() {
        String expression = resultReference.getText().toString();
        char[] tokens = expression.toCharArray();

        // Stack for numbers: 'values'
        Stack<Integer> values = new Stack<Integer>();

        // Stack for Operators: 'ops'
        Stack<Character> ops = new Stack<Character>();

        for (int i = 0; i < tokens.length; i++)
        {

            // Current token is a number, push it to stack for numbers
            if (tokens[i] >= '0' && tokens[i] <= '9')
            {
                StringBuffer sbuf = new StringBuffer();
                // There may be more than one digits in number
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                values.push(Integer.parseInt(sbuf.toString()));
            }

            // Current token is an operator.
            else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/')
            {
                // While top of 'ops' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'ops'
                // to top two elements in values stack
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));

                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
        }

        // Entire expression has been parsed at this point, apply remaining
        // ops to remaining values
        while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));

        // Top of 'values' contains result, return it
        resultReference.setText(values.pop());
    }

    // Returns true if 'op2' has higher or same precedence as 'op1',
    // otherwise returns false.
    public static boolean hasPrecedence(char op1, char op2)
    {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    // A utility method to apply an operator 'op' on operands 'a'
    // and 'b'. Return the result.
    public static int applyOp(char op, int b, int a)
    {
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new
                            UnsupportedOperationException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }

    private void changeResult(String numberPressed){
        String currentText = resultReference.getText().toString();
        if (currentText.equals("0") ){
            if(numberPressed.equals("00")){
                resultReference.setText("0");
            }else {
                resultReference.setText(numberPressed);
            }
        }else{
            resultReference.setText(currentText+numberPressed);
        }
    }
}
