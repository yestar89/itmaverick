package kr.co.company.it_maverick.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import kr.co.company.it_maverick.Adapter.TabPagerAdapter;
import kr.co.company.it_maverick.R;
//import kr.co.company.it_maverick.FeedFragment;


public class ClubFragment extends Fragment {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    // 프래그먼트 어댑터 생성 및 ViewPager2에 설정
    private TabPagerAdapter adapter;

    //private androidx.fragment.app.FragmentManager fragmentManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_club, container, false);

        // XML에서 정의한 뷰 요소 초기화
        viewPager = rootView.findViewById(R.id.container);
        tabLayout = rootView.findViewById(R.id.tabs);


        adapter = new TabPagerAdapter(getActivity());

        viewPager.setAdapter(adapter);
        // TabLayout과 ViewPager2를 연결
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // 각 탭의 텍스트를 설정
            switch (position) {
                case 0:
                    tab.setText("NO TIME 피드");
                    break;
                case 1:
                    tab.setText("YES! 다이어리");
                    break;
                case 2:
                    tab.setText("나만의 팁쳐");
                    break;
            }
        }).attach();

        return rootView;
    }






    }
