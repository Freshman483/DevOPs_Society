package com.shimmita.devopssociety.mains;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.shimmita.devopssociety.R;

import es.dmoral.toasty.Toasty;

public class MakePayments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payments);

        alertUserOfVerificationForPayment();

    }

    private void alertUserOfVerificationForPayment() {
        new AlertDialog.Builder(MakePayments.this)
                .setIcon(R.drawable.ic_baseline_payment_24)
                .setCancelable(false)
                .setTitle("Payment of Ksh. 100")
                .setMessage("\nYou will get registered as a user with  privileged roles on payment of non refundable fee of ksh.100 only.\n" +
                        "\n Benefits :\n1.you will be able to work on jobs posted by clients on this platform,by selecting the one which is appropriate for you.\n" +
                        "\n2.you will unlock very golden study materials of software(programming),networking,hacking,virus_tricks,the power of linux OS and many more.\n" +
                        "\n3.marketing of your final computing Projects if any to the public, to show your incredible abilities\n" +
                        "\n4.connecting your account to the most superior developers in the country and around the world who have approved DeVOps Society\n" +
                        "\n5.Enhancing your job connection to the available clients,institutions or company by forwarding their legitimate links to you !\n")
                .setPositiveButton("Understood, Make Payment", (dialogInterface, i) -> {
                    dialogInterface.dismiss();

                    new AlertDialog.Builder(MakePayments.this)
                            .setTitle("Payment Confirmation")
                            .setIcon(R.drawable.ic_baseline_payment_24)
                            .setCancelable(false)
                            .setMessage("\nDeveloper's number will be forwarded on the dial Screen of your phone use it in making " +
                                    "M-Pesa Payment, once done, (message your registration name or email to the Number). then your account will be approved to SUPPER USER.")
                            .setPositiveButton("Ok,forward the number", (dialogInterface1, i1) -> {

                                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+254757450727")));
                                Toasty.custom(MakePayments.this, "Number Forwarded Successfully", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();
                                dialogInterface1.dismiss();

                                //more Functionality to Be Implemented Here

                                //
                            }).create().show();

                }).setNegativeButton("Understood, not Interested", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    new AlertDialog.Builder(MakePayments.this)
                            .setTitle("Further Notification")
                            .setCancelable(false)
                            .setIcon(R.drawable.ic_baseline_info_24)
                            .setMessage("you will continue  using your account freely but with limited functionalities. " +
                                    "Still you can upgrade later when interested on successful login into your account profile after registration as NORMAL USER\n")
                            .setPositiveButton("Ok", (dialogInterface12, i12) -> {

                               /* startActivity(new Intent(MakePayments.this, LoginIndexMainPage.class));
                                CustomIntent.customType(MakePayments.this, "fadein-to-fadeout");*/

                                dialogInterface12.dismiss();
                            }).create().show();

                }).create().show();

    }
}
