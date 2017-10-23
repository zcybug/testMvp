package baba.jade.android.com.tab.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import baba.jade.android.com.tab.R;
import com.bumptech.glide.Glide;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/10/18 0018  下午 3:22
 * 修改历史：
 * ================================================
 */

public class ImageFragment extends Fragment {

  private static final String IMAGE_URL = "image";
  PhotoView image;
  private String imageUrl;

  public ImageFragment() {
    // Required empty public constructor
  }

  public static ImageFragment newInstance(String param1) {
    ImageFragment fragment = new ImageFragment();
    Bundle args = new Bundle();
    args.putString(IMAGE_URL, param1);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      imageUrl = getArguments().getString(IMAGE_URL);
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_image, container, false);
    image = (PhotoView) view.findViewById(R.id.image);
    Glide.with(getContext()).load(imageUrl).into(image);
    //点击返回
    image.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
      @Override public void onPhotoTap(View view, float x, float y) {
        getActivity().finish();
      }

      @Override public void onOutsidePhotoTap() {
        getActivity().finish();
      }
    });
    return view;
  }
}
