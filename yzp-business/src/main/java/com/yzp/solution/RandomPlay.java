package com.yzp.solution;

import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.RandomUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * desc
 *
 * @author:YangZhiPeng
 * @date: 2024/7/8 16:14
 */
public class RandomPlay {

	//你正在为一个音乐播放器应用开发“随机播放”功能。该功能需要从播放列表中随机选择一首歌进行播放，同时确保同一首歌不会连续播放两次。

	static List<Integer> list = new ArrayList<Integer>();


	public RandomPlay() {
		for (int i = 1; i < 101; i++) {
			list.add(i);
		}
	}

	static HashSet<Integer> hashSet = new HashSet<>();

	public static int randomPlay() {
		int index;
		int id;
		do {
			index = RandomUtil.randomInt(0, 100);
			id = list.get(index);
		} while (hashSet.contains(id));
		hashSet.add(id);
		return id;
	}

	public static void main(String[] args) {

		RandomPlay testController = new RandomPlay();
		List<Integer> allList = new ArrayList<>();
		TimeInterval timeInterval = new TimeInterval();
		for (int i = 1; i < 101; i++) {
			int id = randomPlay();
			System.out.println("第" + i + "次播放id：" + id);
			allList.add(id);
		}
		System.out.println("耗时" + timeInterval.interval());
		allList.stream().sorted().forEach(item -> {
			System.out.print(item + "、");
		});
	}
}
