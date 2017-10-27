package mvp.android.com.mvplib.version;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import mvp.android.com.mvplib.R;
import mvp.android.com.mvplib.log.KLog;
import mvp.android.com.mvplib.utils.ToastUtil;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

import static android.R.attr.id;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/10/27 0027  下午 3:40
 * 修改历史：
 * ================================================
 */

public class Version implements Serializable {
  public boolean hasNew;
  public String upgradeVersion;
  public String upgradeMessage;
  public String upgradeSize;
  public String downloadUrl;
  public boolean isForced;
  public String fileMd5;

  public Version() {
  }

  public static void check(final FragmentActivity absUI, final Version.VersionCallback callback) {
    if (callback != null) {
      if (VersionService.isRunning) {
        KLog.e("正在下载");
      } else {
        callback.accessServer(new Callback<Version>() {
          @Override public void success(Version version) {
            if (version.hasNew) {
              Version.VersionDialog dialog = new Version.VersionDialog();
              Bundle bundle = new Bundle();
              bundle.putSerializable("extra:item", version);
              dialog.setArguments(bundle);
              if (callback.hasNew()) {
                dialog.show(absUI.getSupportFragmentManager(), this.toString());
                dialog.versionCallback(callback);
              }
            } else {
              callback.noNew(absUI.getResources().getString(R.string.abs_version_no_new));
            }
          }

          @Override public void failure(int code, String failure) {
            callback.noNew(failure);
          }
        });
      }
    }
  }

  public abstract static class VersionCallback {
    public VersionCallback() {
    }

    public abstract void accessServer(Callback<Version> var1);

    public boolean hasNew() {
      return true;
    }

    public void download(String url, final String localPath, final Callback<Integer> callback) {
      Request request = (new Request.Builder()).url(url).build();
      OkHttpClient client =
          (new okhttp3.OkHttpClient.Builder()).connectTimeout(30L, TimeUnit.SECONDS)
              .writeTimeout(120L, TimeUnit.SECONDS)
              .readTimeout(120L, TimeUnit.SECONDS)
              .build();
      Call call = client.newCall(request);
      call.enqueue(new okhttp3.Callback() {
        @Override public void onFailure(Call call, IOException e) {
          e.printStackTrace();
          callback.failure(1, "连接异常");
        }

        @Override public void onResponse(Call call, Response response) throws IOException {
          File file = new File(localPath);
          if (file.exists()) {
            file.delete();
          }

          file.createNewFile();
          Sink sink = Okio.sink(file);
          final Source source = Okio.source(response.body().byteStream());
          final long totalSize = response.body().contentLength();
          BufferedSink bufferedSink = Okio.buffer(sink);
          bufferedSink.writeAll(new ForwardingSource(source) {
            long sum = 0L;
            int oldRate = 0;

            @Override public long read(Buffer sink, long byteCount) throws IOException {
              long readSize = super.read(sink, byteCount);
              if (readSize != -1L) {
                this.sum += readSize;
                int rate = Math.round((float) this.sum * 1.0F / (float) totalSize * 100.0F);
                if (this.oldRate != rate) {
                  callback.success(Integer.valueOf(rate));
                  this.oldRate = rate;
                }
              }

              return readSize;
            }
          });
          bufferedSink.flush();
          Util.closeQuietly(sink);
          Util.closeQuietly(source);
          callback.success(Integer.valueOf(101));
        }
      });
    }

    public void noNew(String msg) {
    }
  }

  public static class VersionDialog extends DialogFragment implements View.OnClickListener {
    private ImageView versionHead;
    private TextView versionTitle;
    private TextView versionMessage;
    private TextView versionButton;
    private NumberProgress downloadProgress;
    private View closeLayout;
    private View closeView;
    private Version version;
    private Version.VersionCallback versionCallback;
    private ServiceConnection conn = new ServiceConnection() {
      @Override public void onServiceConnected(ComponentName name, IBinder service) {
        if (VersionDialog.this.version != null) {
          ((VersionService.DownloadBinder) service).start(VersionDialog.this.version,
              VersionDialog.this.versionCallback, new Callback<Integer>() {
                @Override public void success(final Integer integer) {
                  KitThread.runOnMainThreadSync(new Runnable() {
                    public void run() {
                      try {
                        if (integer.intValue() == 101) {
                          VersionDialog.this.dismiss();
                        } else {
                          VersionDialog.this.downloadProgress.setVisibility(0);
                          VersionDialog.this.downloadProgress.setProgress(integer.intValue());
                        }
                      } catch (Exception var2) {
                        var2.printStackTrace();
                        VersionDialog.this.closeLayout.setVisibility(0);
                      }
                    }
                  });
                }

                @Override public void failure(int code, final String failure) {
                  KitThread.runOnMainThreadSync(new Runnable() {
                    public void run() {
                      try {
                        VersionDialog.this.versionCallback.noNew(failure);
                        VersionDialog.this.dismiss();
                      } catch (Exception var2) {
                        VersionDialog.this.closeLayout.setVisibility(0);
                      }
                    }
                  });
                }
              });
        }
      }

      @Override public void onServiceDisconnected(ComponentName name) {
      }
    };

    public VersionDialog() {
    }

    public void versionCallback(Version.VersionCallback versionCallback) {
      this.versionCallback = versionCallback;
    }

    @Override public final View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
      this.getDialog().requestWindowFeature(1);
      this.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
      return inflater.inflate(R.layout.abs_version, (ViewGroup) null);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      this.versionHead = (ImageView) view.findViewById(R.id.abs_version_dialog_head);
      this.versionTitle = (TextView) view.findViewById(R.id.abs_version_dialog_title);
      this.versionMessage = (TextView) view.findViewById(R.id.abs_version_dialog_message);
      this.versionButton = (TextView) view.findViewById(R.id.abs_version_dialog_button);
      this.downloadProgress =
          (NumberProgress) view.findViewById(R.id.abs_version_dialog_download_progress);
      this.closeLayout = view.findViewById(R.id.abs_version_dialog_close_layout);
      this.closeView = view.findViewById(R.id.abs_version_dialog_close);
      this.versionButton.setOnClickListener(this);
      this.closeView.setOnClickListener(this);
      this.bindValue();
    }

    public void bindValue() {
      this.version = (Version) this.getArguments().getSerializable("extra:item");
      if (this.version != null) {
        this.versionTitle.setText(
            String.format(this.getResources().getString(R.string.abs_version_is_to_upgrade),
                new Object[] { this.version.upgradeVersion }));
        String message = "";
        if (!KitCheck.isEmpty(this.version.upgradeSize)) {
          message = String.format(this.getResources().getString(R.string.abs_version_upgrade_size),
              new Object[] { this.version.upgradeSize }) + "\n\n";
        }

        if (!KitCheck.isEmpty(this.version.upgradeMessage)) {
          message = message + this.version.upgradeMessage;
        }

        this.versionMessage.setText(message);
        this.versionButton.setTag(this.version.downloadUrl);
        if (this.version.isForced) {
          this.closeLayout.setVisibility(8);
          this.setCancelable(false);
        }
      }
    }

    @Override public void onClick(View view) {
      if (view.getId() == R.id.abs_version_dialog_button) {
        String url = view.getTag() + "";
        if (!KitCheck.isEmpty(url)) {
          this.versionButton.setVisibility(4);
          VersionService.bindService(view.getContext().getApplicationContext(), this.conn);
          return;
        }
      }

      this.dismiss();
    }
  }
}
