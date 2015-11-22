package com.molbase.articlePush.data;
import java.util.concurrent.ArrayBlockingQueue;
import org.apache.log4j.Logger;
import com.molbase.articlePush.Config;
import com.molbase.articlePush.pojos.ArticleBean;
public class DataRepertory {

	private Logger logger = Logger.getLogger(DataRepertory.class);

	private static DataRepertory repertory;

	private boolean flag = true;

	private boolean fetch = false;//

	private ArrayBlockingQueue<ArticleBean> queue = null;

	private DataRepertory() {
		queue = new ArrayBlockingQueue<ArticleBean>(2000);
	}

	private synchronized static void init() {
		if (repertory == null) {
			repertory = new DataRepertory();
		}
	}

	public static DataRepertory getInstance() {

		if (repertory == null) {
			init();
		}
		return repertory;

	}

	public void put(ArticleBean article) {
		try {
			queue.put(article);
		} catch (InterruptedException e) {
			logger.error(
					String.format("提交文章到仓库的线程被打断,文章标题：%s", article.getTitle()),
					e);
		}
	}

	public ArticleBean take() {
		try {
			return queue.take();
		} catch (InterruptedException e) {
			logger.error(
					String.format("从仓库获取文章出错的线程被打断,queue的长度：%s", queue.size()),
					e);
			return null;
		}
	}

	public int size() {
		return queue.size();
	}

	/*class FetchDataThread extends Thread {
		@Override
		public void run() {
			while (flag) {
				if (size() <= Config.MIN_SIZE) {
					// 开始取数据
					
				}

				try {
					Thread.sleep(Config.INTERVAL);
				} catch (InterruptedException e) {
					logger.error("抓取数据线程被打断", e);
				}
			}
		}
	}*/
}
