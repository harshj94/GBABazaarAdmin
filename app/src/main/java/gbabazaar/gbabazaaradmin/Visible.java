package gbabazaar.gbabazaaradmin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Visible extends Fragment {

    public static Boolean st = false;
    ListView listView;
    ParseObject parseObject;
    ArrayList<Item> items;
    ParseQuery<ParseObject> parseQuery;
    Item item;
    Boolean result;
    int i;
    private ItemsAdapter adapter;

    public Visible() {
    }

    public static Visible newInstance() {
        return new Visible();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.visible, container, false);
        listView = (ListView) rootView.findViewById(R.id.list);
        items = new ArrayList<>();
        adapter = new ItemsAdapter(getActivity().getApplicationContext(), items);
        listView.setAdapter(adapter);
        Toast.makeText(getActivity().getApplicationContext(), "Please wait ads are being loaded.", Toast.LENGTH_SHORT).show();
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            Boolean result = new ConnectionDetector(getActivity().getApplicationContext()).isConnectingToInternet();
                            if (result) {
                                new AdLoad().execute();
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "It seems as if you are not connected to internet.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ignored) {
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 60000);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (st) {
            new AdLoad().execute();
            st = false;
        }
    }

    private class AdLoad extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            result = new ConnectionDetector(getActivity().getApplicationContext()).isConnectingToInternet();
            if (result) {
                items.clear();
                List<ParseObject> objects = null;
                parseQuery = ParseQuery.getQuery("Agriculture");
                parseQuery.whereContains("Status", "accepted");
                parseQuery.orderByDescending("createdAt");
                parseQuery.setLimit(1000);
                try {
                    objects = parseQuery.find();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (objects != null) {
                    for (i = 0; i < objects.size(); i++) {
                        parseObject = objects.get(i);
                        item = new Item();
                        item.settTitle(parseObject.getString("Title"));
                        item.settCategory(parseObject.getString("Category"));
                        item.settObjectId(parseObject.getObjectId());
                        ParseFile parseFile = parseObject.getParseFile("image0");
                        item.settURL(parseFile.getUrl());
                        items.add(item);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if (!result) {
                Toast.makeText(getActivity().getApplicationContext(), "It seems as if you are not connected to internet.", Toast.LENGTH_LONG).show();
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent it = new Intent(getActivity(), VisibleAdDetails.class);
                    Item i1 = items.get(i);
                    it.putExtra("category", i1.gettCategory());
                    it.putExtra("objectId", i1.gettObjectId());
                    startActivity(it);
                }
            });
            new AdLoad1().execute();
        }
    }

    private class AdLoad1 extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            result = new ConnectionDetector(getActivity().getApplicationContext()).isConnectingToInternet();
            if (result) {
                List<ParseObject> objects = null;
                parseQuery = ParseQuery.getQuery("Home");
                parseQuery.whereContains("Status", "accepted");
                parseQuery.orderByDescending("createdAt");
                parseQuery.setLimit(1000);
                try {
                    objects = parseQuery.find();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (objects != null) {
                    for (i = 0; i < objects.size(); i++) {
                        parseObject = objects.get(i);
                        item = new Item();
                        item.settTitle(parseObject.getString("Title"));
                        item.settCategory(parseObject.getString("Category"));
                        item.settObjectId(parseObject.getObjectId());
                        ParseFile parseFile = parseObject.getParseFile("image0");
                        item.settURL(parseFile.getUrl());
                        items.add(item);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if (!result) {
                Toast.makeText(getActivity().getApplicationContext(), "It seems as if you are not connected to internet.", Toast.LENGTH_LONG).show();
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent it = new Intent(getActivity(), VisibleAdDetails.class);
                    Item i1 = items.get(i);
                    it.putExtra("category", i1.gettCategory());
                    it.putExtra("objectId", i1.gettObjectId());
                    startActivity(it);
                }
            });
            new AdLoad2().execute();
        }
    }

    private class AdLoad2 extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            result = new ConnectionDetector(getActivity().getApplicationContext()).isConnectingToInternet();
            if (result) {
                List<ParseObject> objects = null;
                parseQuery = ParseQuery.getQuery("Fruits");
                parseQuery.whereContains("Status", "accepted");
                parseQuery.orderByDescending("createdAt");
                parseQuery.setLimit(1000);
                try {
                    objects = parseQuery.find();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (objects != null) {
                    for (i = 0; i < objects.size(); i++) {
                        parseObject = objects.get(i);
                        item = new Item();
                        item.settTitle(parseObject.getString("Title"));
                        item.settCategory(parseObject.getString("Category"));
                        item.settObjectId(parseObject.getObjectId());
                        ParseFile parseFile = parseObject.getParseFile("image0");
                        item.settURL(parseFile.getUrl());
                        items.add(item);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if (!result) {
                Toast.makeText(getActivity().getApplicationContext(), "It seems as if you are not connected to internet.", Toast.LENGTH_LONG).show();
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent it = new Intent(getActivity(), VisibleAdDetails.class);
                    Item i1 = items.get(i);
                    it.putExtra("category", i1.gettCategory());
                    it.putExtra("objectId", i1.gettObjectId());
                    startActivity(it);
                }
            });
            new AdLoad3().execute();
        }
    }

    private class AdLoad3 extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            result = new ConnectionDetector(getActivity().getApplicationContext()).isConnectingToInternet();
            if (result) {
                List<ParseObject> objects = null;
                parseQuery = ParseQuery.getQuery("Vegetables");
                parseQuery.whereContains("Status", "accepted");
                parseQuery.orderByDescending("createdAt");
                parseQuery.setLimit(1000);
                try {
                    objects = parseQuery.find();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (objects != null) {
                    for (i = 0; i < objects.size(); i++) {
                        parseObject = objects.get(i);
                        item = new Item();
                        item.settTitle(parseObject.getString("Title"));
                        item.settCategory(parseObject.getString("Category"));
                        item.settObjectId(parseObject.getObjectId());
                        ParseFile parseFile = parseObject.getParseFile("image0");
                        item.settURL(parseFile.getUrl());
                        items.add(item);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if (!result) {
                Toast.makeText(getActivity().getApplicationContext(), "It seems as if you are not connected to internet.", Toast.LENGTH_LONG).show();
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent it = new Intent(getActivity(), VisibleAdDetails.class);
                    Item i1 = items.get(i);
                    it.putExtra("category", i1.gettCategory());
                    it.putExtra("objectId", i1.gettObjectId());
                    startActivity(it);
                }
            });
            new AdLoad4().execute();
        }
    }

    private class AdLoad4 extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            result = new ConnectionDetector(getActivity().getApplicationContext()).isConnectingToInternet();
            if (result) {
                List<ParseObject> objects = null;
                parseQuery = ParseQuery.getQuery("Automobiles");
                parseQuery.whereContains("Status", "accepted");
                parseQuery.orderByDescending("createdAt");
                parseQuery.setLimit(1000);
                try {
                    objects = parseQuery.find();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (objects != null) {
                    for (i = 0; i < objects.size(); i++) {
                        parseObject = objects.get(i);
                        item = new Item();
                        item.settTitle(parseObject.getString("Title"));
                        item.settCategory(parseObject.getString("Category"));
                        item.settObjectId(parseObject.getObjectId());
                        ParseFile parseFile = parseObject.getParseFile("image0");
                        item.settURL(parseFile.getUrl());
                        items.add(item);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if (!result) {
                Toast.makeText(getActivity().getApplicationContext(), "It seems as if you are not connected to internet.", Toast.LENGTH_LONG).show();
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent it = new Intent(getActivity(), VisibleAdDetails.class);
                    Item i1 = items.get(i);
                    it.putExtra("category", i1.gettCategory());
                    it.putExtra("objectId", i1.gettObjectId());
                    startActivity(it);
                }
            });
            new AdLoad5().execute();
        }
    }

    private class AdLoad5 extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            result = new ConnectionDetector(getActivity().getApplicationContext()).isConnectingToInternet();
            if (result) {
                List<ParseObject> objects = null;
                parseQuery = ParseQuery.getQuery("Hotels");
                parseQuery.whereContains("Status", "accepted");
                parseQuery.orderByDescending("createdAt");
                parseQuery.setLimit(1000);
                try {
                    objects = parseQuery.find();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (objects != null) {
                    for (i = 0; i < objects.size(); i++) {
                        parseObject = objects.get(i);
                        item = new Item();
                        item.settTitle(parseObject.getString("Title"));
                        item.settCategory(parseObject.getString("Category"));
                        item.settObjectId(parseObject.getObjectId());
                        ParseFile parseFile = parseObject.getParseFile("image0");
                        item.settURL(parseFile.getUrl());
                        items.add(item);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if (!result) {
                Toast.makeText(getActivity().getApplicationContext(), "It seems as if you are not connected to internet.", Toast.LENGTH_LONG).show();
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent it = new Intent(getActivity(), VisibleAdDetails.class);
                    Item i1 = items.get(i);
                    it.putExtra("category", i1.gettCategory());
                    it.putExtra("objectId", i1.gettObjectId());
                    startActivity(it);
                }
            });
            new AdLoad6().execute();
        }
    }

    private class AdLoad6 extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            result = new ConnectionDetector(getActivity().getApplicationContext()).isConnectingToInternet();
            if (result) {
                List<ParseObject> objects = null;
                parseQuery = ParseQuery.getQuery("Others");
                parseQuery.whereContains("Status", "accepted");
                parseQuery.orderByDescending("createdAt");
                parseQuery.setLimit(1000);
                try {
                    objects = parseQuery.find();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (objects != null) {
                    for (i = 0; i < objects.size(); i++) {
                        parseObject = objects.get(i);
                        item = new Item();
                        item.settTitle(parseObject.getString("Title"));
                        item.settCategory(parseObject.getString("Category"));
                        item.settObjectId(parseObject.getObjectId());
                        ParseFile parseFile = parseObject.getParseFile("image0");
                        item.settURL(parseFile.getUrl());
                        items.add(item);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (!result) {
                Toast.makeText(getActivity().getApplicationContext(), "It seems as if you are not connected to internet.", Toast.LENGTH_LONG).show();
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent it = new Intent(getActivity(), VisibleAdDetails.class);
                    Item i1 = items.get(i);
                    it.putExtra("category", i1.gettCategory());
                    it.putExtra("objectId", i1.gettObjectId());
                    startActivity(it);
                }
            });
            adapter.notifyDataSetChanged();
        }
    }
}
