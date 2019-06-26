package com.br.ufc.trabalhotcc.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.br.ufc.trabalhotcc.R;
import com.br.ufc.trabalhotcc.connection.Connect;
import com.br.ufc.trabalhotcc.models.MenuItem;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private GridLayout gridLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Home() {
        // Required empty public constructor
    }

    int countItems = 0;
    int countItemsNow = 0;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        // Get the GridLayout layout object.
        gridLayout = (GridLayout) view.findViewById(R.id.itensMenu);

        TextView passwordTextView = new TextView(getContext());
        // Set password label text value.
        passwordTextView.setText("Password "+ ":");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<MenuItem> items = new Connect().getAllItens(getContext());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (final com.br.ufc.trabalhotcc.models.MenuItem item: items){
                                countItems++;


                                //Relative Layout
//                                RelativeLayout linearLayout = new RelativeLayout(getContext());

//                                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
  //                              param.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
    //                            param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED,1f);

      //                          linearLayout.setLayoutParams(param);
//
  //                              RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
    //                                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                                //ImageButton buttonView = (ImageButton) LayoutInflater.from(getContext()).inflate(R.layout.button, null, false);

                                View view = LayoutInflater.from(getContext()).inflate(R.layout.button, null, false);

                                ConstraintLayout rl = view.findViewById(R.id.container);

                                ProgressBar pg = view.findViewById(R.id.progressBar1);

                                if(view.getParent() != null) {
                                    ((ViewGroup)view.getParent()).removeView(view);
                                }

                                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                                param.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                                param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED,1f);

                                rl.setLayoutParams(param);

                                ImageButton buttonView = view.findViewById(R.id.button1);
                                buttonView.setScaleType(ImageView.ScaleType.FIT_XY);

                                buttonView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        onButtonPressed(item);
                                    }
                                });

                                if(item.getImageFile()!=null){
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            byte[] decodedString = Base64.decode(item.getImageFile(), Base64.DEFAULT);
                                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                            try {
                                                Thread.sleep(1000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    buttonView.setImageBitmap(decodedByte);
                                                    pg.setVisibility(View.INVISIBLE);
                                                }
                                            });
                                        }
                                    }).start();
                                }else {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Bitmap b = new Connect().getImageFromUrl(item.getImage());
                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        buttonView.setImageBitmap(b);
                                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                        b.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                                                        byte[] bytes = baos.toByteArray();
                                                        String encodedImage = Base64.encodeToString(bytes, Base64.DEFAULT);
                                                        item.setImageFile(encodedImage);
                                                        countItemsNow++;
                                                        if(countItemsNow==countItems){
                                                            finishItems(items);
                                                        }
                                                        pg.setVisibility(View.INVISIBLE);
                                                    }
                                                });
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
                                }

                                gridLayout.addView(view);

                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return view;
    }

    /*
      <ImageButton
            android:id="@+id/imageButton"
            android:background="@android:color/transparent"
            android:layout_width="wrap_content"
            android:layout_columnWeight="1"
            android:layout_height="wrap_content"
            tools:src="@tools:sample/avatars" />
     */

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(MenuItem menuItem) {
        if (mListener != null) {
            mListener.onFragmentInteraction(menuItem);
        }
    }

    private void finishItems(List<MenuItem> lista){
        new Connect().saveItensToLocal(getContext(), lista);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(MenuItem menu);
    }
}
