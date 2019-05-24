package anmol.com.shareblood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackActivity extends AppCompatActivity {

    RatingBar mRatingBar;
    TextView mRatingScale;
    EditText mFeedback;
    Button mSendFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

//        Toolbar toolbar =  findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        mRatingScale = (TextView) findViewById(R.id.tvRatingScale);
        mFeedback = (EditText) findViewById(R.id.etFeedback);
        mSendFeedback = (Button) findViewById(R.id.btnSubmit);

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mRatingScale.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        mRatingScale.setText("Very bad");
                        break;
                    case 2:
                        mRatingScale.setText("Need some improvement");
                        break;
                    case 3:
                        mRatingScale.setText("Good");
                        break;
                    case 4:
                        mRatingScale.setText("Great");
                        break;
                    case 5:
                        mRatingScale.setText("Awesome. I love it");
                        break;
                    default:
                        mRatingScale.setText("");
                }
            }
        });

        mSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFeedback.getText().toString().isEmpty()) {
                    Toast.makeText(FeedbackActivity.this, "Please fill in feedback text box", Toast.LENGTH_LONG).show();
                } else {
//                    mFeedback.setText("");
                   // mRatingBar.setRating(0);
                   // Toast.makeText(FeedbackActivity.this, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
                    Intent Email = new Intent(Intent.ACTION_SEND);
                    Email.setType("text/email");
                    Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "lonzo.precious.30@gmail.com" });
                    Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                    Email.putExtra(Intent.EXTRA_TEXT, "Hello Developer,\n"+ "This Application is: "+mRatingScale.getText().toString()+"\n"+mFeedback.getText().toString());
                   // Email.putExtra(Intent.EXTRA_TEXT, "\n"+mFeedback.getText().toString());
                    startActivity(Intent.createChooser(Email, "Send Feedback:"));
                }
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
