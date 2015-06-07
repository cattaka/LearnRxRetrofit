package net.cattaka.learnrxretrofit;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import net.cattaka.learnrxretrofit.retrofit.GitHubService;
import net.cattaka.learnrxretrofit.retrofit.Repo;

import java.util.List;

import retrofit.RestAdapter;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private ListView mListView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mListView = (ListView) view.findViewById(R.id.listview);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
                .build();
        GitHubService service = restAdapter.create(GitHubService.class);

        Observable<List<Repo>> observable = service.listRepos("cattaka").subscribeOn(Schedulers.io());

        AppObservable.bindFragment(this, observable).subscribe(new Action1<List<Repo>>() {
            @Override
            public void call(List<Repo> repos) {
                ArrayAdapter<Repo> adapter = new ArrayAdapter<Repo>(getActivity(), android.R.layout.simple_expandable_list_item_1, repos);
                mListView.setAdapter(adapter);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
