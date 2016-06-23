package com.michalkarmelita.marvelcomics.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.michalkarmelita.marvelcomics.R;

public class DialogUtils {

    public static void showInfoDialog(Context context, final BudgetListener budgetListener, String currentBudget) {

        final View view = LayoutInflater.from(context).inflate(R.layout.budget_dialog, null);
        final EditText budget = (EditText) view.findViewById(R.id.budget);
        if (currentBudget != null) {
            budget.setText(currentBudget);
        }

        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Set Budget Filter")
                .setView(view)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        budgetListener.onSetBudget(budget.getText().toString());
                    }
                })
                .create();
            dialog.show();
    }

    public interface BudgetListener {

        void onSetBudget(String budget);

    }
}
