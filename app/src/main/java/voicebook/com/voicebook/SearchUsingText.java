package voicebook.com.voicebook;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchUsingText extends AppCompatActivity {

    private ImageButton mSpeakBtn;
    private TextView output;
    private static long back;
    private TextView outputHead;
    private EditText inputText;
    private Button search;
    private Button voice;
    private ListView listview;
    public String word = " ";
    public String outputString = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_using_text);

        mSpeakBtn = findViewById(R.id.btnSpeak);
        output = findViewById(R.id.giveOutput);
        inputText = findViewById(R.id.input);
        outputHead = findViewById(R.id.OutputHead);
        search = findViewById(R.id.search);
        voice = findViewById(R.id.voice);
        listview = findViewById(R.id.listView);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFromList = (String) listview.getItemAtPosition(position);
                String[] text = selectedFromList.split(" ");
                String firstName = text[1];
                String[] lName = text[2].split("\n");
                String lastName = lName[0];
                Intent intent = new Intent(getApplicationContext() , Details.class);
                intent.putExtra("fName" , firstName);
                intent.putExtra("lName" , lastName);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word = inputText.getText().toString().trim();
                if (word.equals("")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(SearchUsingText.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Please Enter Something in input field.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
//                    try {

                    findData(word);
//
//                    } catch (Exception e) {
//                        Toast.makeText(getApplicationContext(), "Give Valid Input\n" + word + " is invalid Input\nCheck Help in menu for more information", Toast.LENGTH_LONG).show();Intent intent = new Intent(SearchUsingText.this, SearchUsingText.class);
//                        startActivity(intent);
//                        finish();
//                    }
                }

            }
        });

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchUsingText.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    @SuppressLint("SetTextI18n")
    private void findData(String word) {
        {
            int check = findCarDetails(word);
            if(check!=1)
            {
                String columns = findColumns(word);
                String[] column = columns.split(" ");
                String[] name = new String[2];
                if (columns.equals("n/a")) {
                    outputHead.setText("You said:" + word);
                    AlertDialog alertDialog = new AlertDialog.Builder(SearchUsingText.this).create();
                    alertDialog.setTitle("Hey!");
                    alertDialog.setMessage("Please Say something that application can search in database like address of a person.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    name = findName(word);
                }
                if (name[0] == "n/a" && name[1] == "n/a") {
                    outputHead.setText("You said:" + word);
                    AlertDialog alertDialog = new AlertDialog.Builder(SearchUsingText.this).create();
                    alertDialog.setTitle("Hey!");
                    alertDialog.setMessage("No person found of specific name.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    outputHead.setText("You said:" + word);
                    output.setText(" ");
                    DisplayResult(column, name);
                }
            }
            }


    }

    private int findCarDetails(String word) {

       String[] words = word.split(" ");
        String[] outputStringList = new String[]{

        };
        final List<String> output_list = new ArrayList<String>(Arrays.asList(outputStringList));
       for(int i=0;i < words.length ; i++)
       {
           InputStream is = getResources().openRawResource(R.raw.contacts);
           BufferedReader reader = new BufferedReader(
                   new InputStreamReader(is, Charset.forName("UTF-8"))
           );
           String line = " ";
           try {
               while ((line = reader.readLine()) != null) {
                   String[] tokens = line.split(",");

                   if ((words[i].toLowerCase()).equals(tokens[10].toLowerCase())) {
                       output.setText("Name: " + tokens[1]+ " " + tokens[2]);
                       output.append("\nAddress : " + tokens[3]);
                       output.append("\nOffice Intercom : " + tokens[4]);
                       output.append("\nHome Intercom : " + tokens[5]);
                       output.append("\nMobile Number : " + tokens[6]);
                       output.append("\nEmail : " + tokens[7]);
                       output.append("\nDepartment : " + tokens[8]);
                       output.append("\nData Of birth : " + tokens[11]);
                       output.append("\nVehicle Number : " + tokens[10]);
                       output.append("\nDesignation : " + tokens[9]);
                       outputString = output.getText().toString() + "\n";
                       output_list.add(outputString);
                       output.setText("");
                       final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                               (this, android.R.layout.simple_list_item_1, output_list) {
                           @Override
                           public View getView(int position, View convertView, ViewGroup parent) {
                               // Get the Item from ListView
                               View view = super.getView(position, convertView, parent);

                               // Initialize a TextView for ListView each Item
                               TextView tv = (TextView) view.findViewById(android.R.id.text1);

                               // Set the text color of TextView (ListView Item)
                               tv.setTextColor(Color.YELLOW);

                               // Generate ListView Item using TextView
                               return view;
                           }
                       };

                       // DataBind ListView with items from ArrayAdapter
                       listview.setAdapter(arrayAdapter);
                       outputHead.setText("You Said :"+word);
                       return 1;
                   }
               }
           } catch (IOException e) {
               Log.wtf("MainActvity", "Error Reading DataFile line = " + line);

               e.printStackTrace();
           }

       }
        return 0;
    }

    private void DisplayResult(String[] column, String[] name) {
        String[] outputStringList = new String[]{

        };
        final List<String> output_list = new ArrayList<String>(Arrays.asList(outputStringList));
        if (name[1] == "n/a") {
            InputStream is = getResources().openRawResource(R.raw.contacts);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8"))
            );
            String line = " ";
            try {
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(",");
                    if (name[0].equals(tokens[1])) {
                        output.setText("Name: " + tokens[1] + " " + tokens[2]);

                        for (int j = 0; j < column.length; j++) {
                            if (column[j].equals("*")) {
                                output.append("\nAddress : " + tokens[3]);
                                output.append("\nOffice Intercom : " + tokens[4]);
                                output.append("\nHome Intercom : " + tokens[5]);
                                output.append("\nMobile Number : " + tokens[6]);
                                output.append("\nEmail : " + tokens[7]);
                                output.append("\nDepartment : " + tokens[8]);
                                output.append("\nData Of birth : " + tokens[11]);
                                output.append("\nVehicle Number : " + tokens[10]);
                                output.append("\nDesignation : " + tokens[9]);
                                break;
                            } else if (column[j].equals("address"))
                                output.append("\nAddress : " + tokens[3]);
                            else if (column[j].equals("intercomOffice"))
                                output.append("\nOffice Intercom : " + tokens[4]);
                            else if (column[j].equals("intercomHome"))
                                output.append("\nHome Intercom : " + tokens[5]);
                            else if (column[j].equals("mobile"))
                                output.append("\nMobile Number : " + tokens[6]);
                            else if (column[j].equals("email"))
                                output.append("\nEmail : " + tokens[7]);
                            else if (column[j].equals("dept"))
                                output.append("\nDepartment : " + tokens[8]);
                            else if (column[j].equals("post"))
                                output.append("\nDesignation : " + tokens[9]);
                            else if (column[j].equals("vehicle"))
                                output.append("\nVehicle Number : " + tokens[10]);
                            else if (column[j].equals("date"))
                                output.append("\nData Of birth : " + tokens[11]);

                        }
                        outputString = output.getText().toString() + "\n";
                        output_list.add(outputString);
                        output.setText("");
                    }
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                            (this, android.R.layout.simple_list_item_1, output_list) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            // Get the Item from ListView
                            View view = super.getView(position, convertView, parent);

                            // Initialize a TextView for ListView each Item
                            TextView tv = (TextView) view.findViewById(android.R.id.text1);

                            // Set the text color of TextView (ListView Item)
                            tv.setTextColor(Color.YELLOW);

                            // Generate ListView Item using TextView
                            return view;
                        }
                    };

                    // DataBind ListView with items from ArrayAdapter
                    listview.setAdapter(arrayAdapter);


                }
            } catch (IOException e) {
                Log.wtf("MainActvity", "Error Reading DataFile line = " + line);

                e.printStackTrace();
            }
        } else if (name[0] == "n/a") {
            InputStream is = getResources().openRawResource(R.raw.contacts);
            BufferedReader reader;
            reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8"))
            );
            String line = " ";
            try {
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(",");
                    if (name[1].equals(tokens[2])) {
                        output.setText("Name: " + tokens[1] + " " + tokens[2]);

                        for (int j = 0; j < column.length; j++) {
                            if (column[j].equals("*")) {
                                output.append("\nAddress : " + tokens[3]);
                                output.append("\nOffice Intercom : " + tokens[4]);
                                output.append("\nHome Intercom : " + tokens[5]);
                                output.append("\nMobile Number : " + tokens[6]);
                                output.append("\nEmail : " + tokens[7]);
                                output.append("\nDepartment : " + tokens[8]);
                                output.append("\nData Of birth : " + tokens[11]);
                                output.append("\nVehicle Number : " + tokens[10]);
                                output.append("\nDesignation : " + tokens[9]);
                                break;
                            } else if (column[j].equals("address"))
                                output.append("\nAddress : " + tokens[3]);
                            else if (column[j].equals("intercomOffice"))
                                output.append("\nOffice Intercom : " + tokens[4]);
                            else if (column[j].equals("intercomHome"))
                                output.append("\nHome Intercom : " + tokens[5]);
                            else if (column[j].equals("mobile"))
                                output.append("\nMobile Number : " + tokens[6]);
                            else if (column[j].equals("email"))
                                output.append("\nEmail : " + tokens[7]);
                            else if (column[j].equals("dept"))
                                output.append("\nDepartment : " + tokens[8]);
                            else if (column[j].equals("post"))
                                output.append("\nDesignation : " + tokens[9]);
                            else if (column[j].equals("vehicle"))
                                output.append("\nVehicle Number : " + tokens[10]);
                            else if (column[j].equals("date"))
                                output.append("\nData Of birth : " + tokens[11]);

                        }
                        outputString = output.getText().toString() + "\n";
                        output_list.add(outputString);
                        output.setText("");
                    }
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                            (this, android.R.layout.simple_list_item_1, output_list) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            // Get the Item from ListView
                            View view = super.getView(position, convertView, parent);

                            // Initialize a TextView for ListView each Item
                            TextView tv = (TextView) view.findViewById(android.R.id.text1);

                            // Set the text color of TextView (ListView Item)
                            tv.setTextColor(Color.YELLOW);

                            // Generate ListView Item using TextView
                            return view;
                        }
                    };
                    // DataBind ListView with items from ArrayAdapter
                    listview.setAdapter(arrayAdapter);
                }
            } catch (IOException e) {
                Log.wtf("MainActvity", "Error Reading DataFile line = " + line);

                e.printStackTrace();
            }
        } else if (!(name[0] == "n/a" && name[1] == "n/a")) {
            int count = -1;
            InputStream is = getResources().openRawResource(R.raw.contacts);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8"))
            );
            String line = " ";
            try {
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(",");
                    if (name[0].equals(tokens[1]) && name[1].equals(tokens[2])) {
                        output.setText("Name: " + tokens[1] + " " + tokens[2]);

                        for (int j = 0; j < column.length; j++) {
                            if (column[j].equals("*")) {
                                output.append("\nAddress : " + tokens[3]);
                                output.append("\nOffice Intercom : " + tokens[4]);
                                output.append("\nHome Intercom : " + tokens[5]);
                                output.append("\nMobile Number : " + tokens[6]);
                                output.append("\nEmail : " + tokens[7]);
                                output.append("\nDepartment : " + tokens[8]);
                                output.append("\nData Of birth : " + tokens[11]);
                                output.append("\nVehicle Number : " + tokens[10]);
                                output.append("\nDesignation : " + tokens[9]);
                                break;
                            } else if (column[j].equals("address"))
                                output.append("\nAddress : " + tokens[3]);
                            else if (column[j].equals("intercomOffice"))
                                output.append("\nOffice Intercom : " + tokens[4]);
                            else if (column[j].equals("intercomHome"))
                                output.append("\nHome Intercom : " + tokens[5]);
                            else if (column[j].equals("mobile"))
                                output.append("\nMobile Number : " + tokens[6]);
                            else if (column[j].equals("email"))
                                output.append("\nEmail : " + tokens[7]);
                            else if (column[j].equals("dept"))
                                output.append("\nDepartment : " + tokens[8]);
                            else if (column[j].equals("post"))
                                output.append("\nDesignation : " + tokens[9]);
                            else if (column[j].equals("vehicle"))
                                output.append("\nVehicle Number : " + tokens[10]);
                            else if (column[j].equals("date"))
                                output.append("\nData Of birth : " + tokens[11]);

                        }
                        outputString = output.getText().toString() + "\n";
                        output_list.add(outputString);
                        output.setText("");
                    }
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                            (this, android.R.layout.simple_list_item_1, output_list) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            // Get the Item from ListView
                            View view = super.getView(position, convertView, parent);

                            // Initialize a TextView for ListView each Item
                            TextView tv = (TextView) view.findViewById(android.R.id.text1);

                            // Set the text color of TextView (ListView Item)
                            tv.setTextColor(Color.YELLOW);

                            // Generate ListView Item using TextView
                            return view;
                        }
                    };
                    // DataBind ListView with items from ArrayAdapter
                    listview.setAdapter(arrayAdapter);
                }
            } catch (IOException e) {
                Log.wtf("MainActvity", "Error Reading DataFile line = " + line);

                e.printStackTrace();
            }
        }

    }

    private String[] findName(String word) {
        String[] FnameLname = {"n/a", "n/a"};
        word = word.toLowerCase();
        String[] words = word.split(" ");
        boolean foundFirst = false, foundLast = false, ignoredWord = false;
        String[] ignored = {"give", "me", "what", "is", "in", "and", "or", "lives", "address", "flat", "stay", "house", "flat", "house", "quator", "department", "number", "contact", "intercom", "office", "intercom", "intercom", "home", "house", "phone", "mobile", "mail", "email", "details", "detail", "information"};
        for (int i = 0; i < words.length; i++) {

            ignoredWord = Arrays.asList(ignored).contains(words[i]);
            if (!ignoredWord && (!foundFirst || !foundLast)) {
                InputStream is = getResources().openRawResource(R.raw.contacts);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, Charset.forName("UTF-8"))
                );
                String line = " ";
                try {
                    while ((line = reader.readLine()) != null) {
                        String[] tokens = line.split(",");
                        if (words[i].equals(tokens[1])) {
                            FnameLname[0] = words[i];
                            foundFirst = true;
                        }
                        if (words[i].equals(tokens[2])) {
                            FnameLname[1] = words[i];
                            foundLast = true;
                        }
                    }

                } catch (IOException e) {
                    Log.wtf("MainActvity", "Error Reading DataFile line = " + line);

                    e.printStackTrace();

                }
            }
        }
        return FnameLname;
    }

    private String findColumns(String word) {

        String[] Address = {"address", "flat", "stay", "house", "flat", "house", "quator", "live", "lives"};
        String[] Dept = {"department"};
        String[] Contact = {"contact", "number"};
        String[] IntercomOffice = {"office"};
        String[] IntercomHome = {"home", "house"};
        String[] Intercom = {"intercom", "landLine", "base"};
        String[] Mobile = {"mobile", "phone"};
        String[] Email = {"email", "mail"};
        String[] All = {"details", "detail", "about", "information", "who"};
        String[] DOB = {"date", "birthday", "birth", "birthdate"};
        String[] Vehicle = {"car", "vehicle", "bike", "motor"};
        String[] Psot = {"position", "designation", "post"};
        String result = "n/a";
        String[] words = word.split(" ");

        if (words.length < 3) {
            return "*";
        }
        int i;
        boolean containsAddress = false;
        boolean containsDept = false;
        boolean containsContact = false;
        boolean containsIntecomOffice = false;
        boolean containsIntercomHome = false;
        boolean containsIntercom = false;
        boolean containsMobile = false;
        boolean containsEmail = false;
        boolean containsAll = false;
        boolean containDOB = false;
        boolean containVehicle = false;
        boolean containPost = false;
        boolean containDOBCheck = true;
        boolean containVehicleCheck = true;
        boolean containPostCheck = true;
        boolean containsAddressCheck = true;
        boolean containsDeptCheck = true;
        boolean containsContactCheck = true;
        boolean containsIntecomOfficeCheck = true;
        boolean containsIntercomHomeCheck = true;
        boolean containsIntercomCheck = true;
        boolean containsMobileCheck = true;
        boolean containsEmailCheck = true;
        boolean containsAllCheck = true;
        int j = 0;
        for (i = 0; i < words.length; i++) {
            containsAddress = Arrays.asList(Address).contains(words[i]);
            containsDept = Arrays.asList(Dept).contains(words[i]);
            containsContact = Arrays.asList(Contact).contains(words[i]);
            containsIntecomOffice = Arrays.asList(IntercomOffice).contains(words[i]);
            containsIntercomHome = Arrays.asList(IntercomHome).contains(words[i]);
            containsIntercom = Arrays.asList(Intercom).contains(words[i]);
            containsMobile = Arrays.asList(Mobile).contains(words[i]);
            containsEmail = Arrays.asList(Email).contains(words[i]);
            containDOB = Arrays.asList(DOB).contains(words[i]);
            containVehicle = Arrays.asList(Vehicle).contains(words[i]);
            containPost = Arrays.asList(Psot).contains(words[i]);
            containsAll = Arrays.asList(All).contains(words[i]);

            if (containDOB && containDOBCheck) {
                containDOBCheck = false;
                result = result + " date";
            } else if (containPost && containPostCheck) {
                containPostCheck = false;
                result = result + " post";
            } else if (containVehicle && containVehicleCheck) {
                containVehicleCheck = false;
                containsContactCheck = false;
                result = result + " vehicle";
            } else if (containsAddress && containsAddressCheck) {
                result = result + " address";
                containsAddressCheck = false;
            } else if (containsDept && containsDeptCheck) {
                result = result + " dept";
                containsDeptCheck = false;
            } else if (containsIntecomOffice && containsIntecomOfficeCheck) {
                result = result + " intercomOffice";
                containsIntercomCheck = false;
                containsIntecomOfficeCheck = false;
                containsContactCheck = false;
            } else if (containsIntercomHomeCheck && containsIntercomHome) {
                result = result + " intercomHome";
                containsIntercomCheck = false;
                containsContactCheck = false;
                containsIntercomHomeCheck = false;
            } else if (containsIntercom && containsIntercomCheck) {
                result = result + " intercomHome intercomOffice";
                containsIntercomCheck = false;
                containsIntercomHomeCheck = false;
                containsContactCheck = false;
                containsIntecomOfficeCheck = false;
            } else if (containsMobileCheck && containsMobile) {
                result = result + " intercomHome intercomOffice mobile";
                containsMobileCheck = false;
                containsContactCheck = false;
            } else if (containsEmail && containsEmailCheck) {
                result = result + " email";
                containsEmailCheck = false;
            } else if (containsContactCheck && containsContact) {
                result = result + " intercomHome intercomOffice mobile";
                containsContactCheck = false;
                containsIntecomOfficeCheck = false;
                containsMobileCheck = false;
                containsIntercomHome = false;
                containsMobileCheck = false;
            } else if (containsAll && containsAllCheck) {
                result = "*";
                containsAllCheck = false;
                break;
            }
        }
        return result;
    }



    @Override
    public void onBackPressed() {
        if ((back + 2000) > System.currentTimeMillis()) {
            super.onBackPressed();
        } else
            Toast.makeText(getApplicationContext(), "Press Again to exit", Toast.LENGTH_SHORT).show();
        back = System.currentTimeMillis();
    }

    //Manage Menu
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        //respond to menu item selection
        switch (item.getItemId()) {
            case R.id.report:
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Contact IS Department or developer for any issue", Snackbar.LENGTH_LONG);
                snackbar.show();
                return true;
            case R.id.contact:
                String[] TO = {"technoarpitorignal@hotmail.com"};
                String[] CC = {""};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contacting using BR VoiceBook app For : ");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hey Arpit,\n");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail using any Email App"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SearchUsingText.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.request:
                Snackbar snackbar3 = Snackbar.make(findViewById(android.R.id.content), "Contact IS Department for any kind of Update", Snackbar.LENGTH_LONG);
                snackbar3.show();
                return true;

            case R.id.help:
                AlertDialog alertDialog = new AlertDialog.Builder(SearchUsingText.this).create();
                alertDialog.setTitle("Help!");
                alertDialog.setMessage("Its simple!\n" +
                        "1.Tap on input field.\n" +
                        "(Type only name or )\n"+
                        "2.type address/phone number or anything of any employee\n\n" +
                        "3.Wait for the app to respond\n" +
                        "4.DING!\n\nIf you want to search vehicle owner then input vehicle's number without spaces and - or , .");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
