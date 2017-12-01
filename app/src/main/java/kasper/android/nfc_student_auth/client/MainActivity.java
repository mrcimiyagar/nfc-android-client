package kasper.android.nfc_student_auth.client;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;

    private final String studentId = "123456789";
    private final String password = "fs654g31b65s4e98ew7rt";

    private final String systemMimeType = "application/kasper.android.nfc_student_auth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        this.checkNfcAvailability();
        this.initNfcAuthListener();
    }

    private void checkNfcAvailability() {
        if (nfcAdapter == null) {
            Toast.makeText(this, "Sorry this device does not have NFC.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable NFC via Settings.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }

    private void initNfcAuthListener() {
        this.nfcAdapter.setNdefPushMessageCallback(new NfcAdapter.CreateNdefMessageCallback() {
            @Override
            public NdefMessage createNdefMessage(NfcEvent event) {
                return new NdefMessage(NdefRecord.createMime(systemMimeType
                        , (studentId + " " + password).getBytes()));
            }
        }, this);
    }
}
