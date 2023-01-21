package com.google.myapplication_test.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.myapplication_test.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactFragment extends Fragment {

    static String[] items = {"General question","Registration difficulties","Unable to recover password"};
    AutoCompleteTextView autoCompleteTextView, autoComplete;
    EditText problemTextContact, nameContact, emailContact;
    Button buttonSendContact;
    ArrayAdapter<String> adapterItem;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContactFragment() {
        // Required empty public constructor
    }

    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contact, container, false);
        autoCompleteTextView = view.findViewById(R.id.autoComplete);
        problemTextContact = view.findViewById(R.id.problemTextContact);
        emailContact = view.findViewById(R.id.emailContact);
        autoComplete = view.findViewById(R.id.autoComplete);
        buttonSendContact = view.findViewById(R.id.buttonSendContact);

        String[] myList = getResources().getStringArray(R.array.stringsList);

        adapterItem = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,myList);
        autoCompleteTextView.setAdapter(adapterItem);

        buttonSendContact.setOnClickListener(view1 -> {
            if(!problemTextContact.getText().toString().isEmpty() && !autoCompleteTextView.getText().toString().isEmpty() && !emailContact.getText().toString().isEmpty()){
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"travelbuddy@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT,autoCompleteTextView.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT,problemTextContact.getText().toString());
                intent.setType("message/rfc822");
                if(intent.resolveActivity(getContext().getPackageManager()) != null){
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getContext(),"There is no application to support this action!",Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getContext(),"Please fill in all the fields!",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}