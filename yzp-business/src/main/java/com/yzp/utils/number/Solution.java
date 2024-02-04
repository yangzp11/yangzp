package com.yzp.utils.number;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.StrUtil;

import java.util.*;

/**
 * // Regular Expression Matching
 * // Time complexity: O(n)
 * // Space complexity: O(1)
 *
 * @author YangZhiPeng
 * @date 2022/9/22 16:05
 */
public class Solution {

//	public static void main(String[] args) {
//		TimeInterval timeInterval = DateUtil.timer();
//		System.out.println(recursionAdd(999));
//		System.out.println("hs "+timeInterval.interval());
	//String[] words1 = {"a","ab"};
	//		String[] words2 = {"a","a","a","ab"};
//		String[] words1 = {"b", "bb", "bbb"};
//		String[] words2 = {"a", "aa", "aaa"};
//		System.out.println(countWords(words1, words2));
//		System.out.println(sumOfMultiples(10));
	//System.out.println(isMatch("aabbcc", "aabbcc"));
//        TimeInterval timeInterval = DateUtil.timer();
//        System.out.println(isNumber("21645987921.99"));
//        System.out.println("耗时1" + timeInterval.interval());
//
//        TimeInterval timeInterval2 = DateUtil.timer();
//        System.out.println(isNumber2("21645987921.99"));
//        System.out.println("耗时2" + timeInterval2.interval());
//        int[] nums = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12};
//        System.out.println(missingNumber(nums));
//        System.out.println(repeatVar("AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT", 10));
//        int[] nums = {-1, -2, -3, -4, -5};
//        System.out.println(Arrays.toString(twoSum(nums, -8)));
//        int[] nums = {1, 1, 2};
//        System.out.println(removeDuplicates(nums));
	//System.out.println(lengthOfLastWord("fly  Heloo word  "));
//	}

	public static boolean isMatch(final String s, final String p) {
		return isMatch(s, 0, p, 0);
	}

	private static boolean matchFirst(String s, int i, String p, int j) {
		System.out.println(s + "," + i + "," + p + "," + j);
		if (j == p.length()) {
			return i == s.length();
		}
		if (i == s.length()) {
			return j == p.length();
		}
		return p.charAt(j) == '.' || s.charAt(i) == p.charAt(j);
	}

	private static boolean isMatch(String s, int i, String p, int j) {
		if (j == p.length()) return i == s.length();
		// next char is not '*', then must match current character
		//final char b = p.charAt(j);
		if (j == p.length() - 1 || p.charAt(j + 1) != '*') {
			if (matchFirst(s, i, p, j)) return isMatch(s, i + 1, p, j + 1);
			else return false;
		} else { // next char is '*'
			if (isMatch(s, i, p, j + 2)) return true;  // try the length of 0
			while (matchFirst(s, i, p, j))  // try all possible lengths
				if (isMatch(s, ++i, p, j + 2)) return true;
			return false;
		}
	}

	/**
	 * 是否为数字，支持包括：
	 *
	 * <pre>
	 * 1、10进制
	 * 2、16进制数字（0x开头）
	 * 3、科学计数法形式（1234E3）
	 * 4、类型标识形式（123D）
	 * 5、正负数标识形式（+123、-234）
	 * </pre>
	 *
	 * @param str 字符串值
	 * @return 是否为数字
	 */
	public static boolean isNumber(CharSequence str) {
		if (StrUtil.isBlank(str)) {
			return false;
		}
		char[] chars = str.toString().toCharArray();
		int sz = chars.length;
		boolean hasExp = false;
		boolean hasDecPoint = false;
		boolean allowSigns = false;
		boolean foundDigit = false;
		// deal with any possible sign up front
		int start = (chars[0] == '-' || chars[0] == '+') ? 1 : 0;
		if (sz > start + 1) {
			if (chars[start] == '0' && (chars[start + 1] == 'x' || chars[start + 1] == 'X')) {
				int i = start + 2;
				if (i == sz) {
					return false; // str == "0x"
				}
				// checking hex (it can't be anything else)
				for (; i < chars.length; i++) {
					if ((chars[i] < '0' || chars[i] > '9') && (chars[i] < 'a' || chars[i] > 'f') && (chars[i] < 'A' || chars[i] > 'F')) {
						return false;
					}
				}
				return true;
			}
		}
		sz--; // don't want to loop to the last char, check it afterwords
		// for type qualifiers
		int i = start;
		// loop to the next to last char or to the last char if we need another digit to
		// make a valid number (e.g. chars[0..5] = "1234E")
		while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
			if (chars[i] >= '0' && chars[i] <= '9') {
				foundDigit = true;
				allowSigns = false;

			} else if (chars[i] == '.') {
				if (hasDecPoint || hasExp) {
					// two decimal points or dec in exponent
					return false;
				}
				hasDecPoint = true;
			} else if (chars[i] == 'e' || chars[i] == 'E') {
				// we've already taken care of hex.
				if (hasExp) {
					// two E's
					return false;
				}
				if (!foundDigit) {
					return false;
				}
				hasExp = true;
				allowSigns = true;
			} else if (chars[i] == '+' || chars[i] == '-') {
				if (!allowSigns) {
					return false;
				}
				allowSigns = false;
				foundDigit = false; // we need a digit after the E
			} else {
				return false;
			}
			i++;
		}
		if (i < chars.length) {
			if (chars[i] >= '0' && chars[i] <= '9') {
				// no type qualifier, OK
				return true;
			}
			if (chars[i] == 'e' || chars[i] == 'E') {
				// can't have an E at the last byte
				return false;
			}
			if (chars[i] == '.') {
				if (hasDecPoint || hasExp) {
					// two decimal points or dec in exponent
					return false;
				}
				// single trailing decimal point after non-exponent is ok
				return foundDigit;
			}
			if (!allowSigns && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F')) {
				return foundDigit;
			}
			if (chars[i] == 'l' || chars[i] == 'L') {
				// not allowing L with an exponent
				return foundDigit && !hasExp;
			}
			// last character is illegal
			return false;
		}
		// allowSigns is true iff the val ends in 'E'
		// found digit it to make sure weird stuff like '.' and '1E-' doesn't pass
		return !allowSigns && foundDigit;
	}

	public static boolean isNumber2(String s) {
		int[][] transitionTable = new int[][]{
			{-1, 0, 3, 1, 2, -1}, // next states for state 0
			{-1, 8, -1, 1, 4, 5},     // next states for state 1
			{-1, -1, -1, 4, -1, -1},     // next states for state 2
			{-1, -1, -1, 1, 2, -1},     // next states for state 3
			{-1, 8, -1, 4, -1, 5},     // next states for state 4
			{-1, -1, 6, 7, -1, -1},     // next states for state 5
			{-1, -1, -1, 7, -1, -1},     // next states for state 6
			{-1, 8, -1, 7, -1, -1},     // next states for state 7
			{-1, 8, -1, -1, -1, -1}     // next states for state 8
		};
		int state = 0;
		for (int i = 0; i < s.length(); ++i) {
			final char ch = s.charAt(i);
			InputType inputType = InputType.INVALID;
			if (Character.isSpaceChar(ch))
				inputType = InputType.SPACE;
			else if (ch == '+' || ch == '-')
				inputType = InputType.SIGN;
			else if (Character.isDigit(ch))
				inputType = InputType.DIGIT;
			else if (ch == '.')
				inputType = InputType.DOT;
			else if (ch == 'e' || ch == 'E')
				inputType = InputType.EXPONENT;
			// Get next state from current state and input symbol
			state = transitionTable[state][inputType.ordinal()];
			// Invalid input
			if (state == -1) return false;
		}
		// If the current state belongs to one of the accepting (final) states,
		// then the number is valid
		return state == 1 || state == 4 || state == 7 || state == 8;
	}

	enum InputType {
		INVALID,    // 0
		SPACE,      // 1
		SIGN,       // 2
		DIGIT,      // 3
		DOT,        // 4
		EXPONENT,   // 5
		NUM_INPUTS  // 6
	}

	public static int missingNumber(int[] nums) {
		int sum = 0;
		for (int x : nums) {
			sum += x;
		}
		final int n = nums.length;
		final int sumExpected = (n * (n + 1)) / 2;
		return sumExpected - sum;
	}

	/**
	 * 查找在var中出现不止一次的所有 length 个字母长的序列（子串）
	 *
	 * @param var
	 * @param length
	 * @return
	 */
	public static List<String> repeatVar(String var, int length) {
		List<String> result = new ArrayList<>();
		if (var.length() < length) return result;

		Map<String, Integer> countMap = new HashMap<>();
		for (int i = 0; i < var.length() - length - 1; ++i) {
			String key = var.substring(i, i + length);
			int value = countMap.getOrDefault(key, 0);
			countMap.put(key, value + 1);
		}
		for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
			if (entry.getValue() > 1) {
				result.add(entry.getKey());
			}
		}
		return result;
	}
//    输入：nums = {-1, -2, -3, -4, -5}; target = -8
//    输出：[2,4]
//    解释：因为 nums[2] + nums[4] == -8 ，返回 [2,4] 。

	public static int[] twoSum(int[] nums, int target) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			int sub = target - nums[i];
			if (map.containsKey(sub)) {
				if (i > map.get(sub)) {
					return new int[]{map.get(sub), i};
				}
				return new int[]{i, map.get(sub)};
			}
			map.put(nums[i], i);
		}
		throw new IllegalArgumentException("No two sum solution");
	}

	//输入：nums = [0,0,1,1,1,2,2,3,3,4]
	//输出：5, nums = [0,1,2,3,4]
	public static int removeDuplicates(int[] nums) {
		List<Integer> list = new ArrayList<>();
		for (int num : nums) {
			if (list.contains(num)) {
				continue;
			}
			list.add(num);
		}
		nums = list.stream().mapToInt(Integer::valueOf).toArray();
		System.out.println(Arrays.toString(nums));
		return list.size();
	}

	public static boolean containsDuplicate(int[] nums) {
		HashSet<Integer> hashSet = new HashSet<>();
		for (int num : nums) {
			if (!hashSet.add(num)) {
				return true;
			}
		}
		return false;
	}

	public static int lengthOfLastWord(String s) {

		String[] str = s.split(" ");
		String last = str[str.length - 1];
		return last.length();
	}

	//输入: s = "abcabcbb"
	//输出: 3
	//解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
	public static int lengthOfLongestSubstring(String s) {

		int n = s.length(), ans = 0;
		Map<Character, Integer> map = new HashMap<>();
		for (int j = 0, i = 0; j < n; j++) {
			if (map.containsKey(s.charAt(j))) {
				i = Math.max(map.get(s.charAt(j)), i);
			}
			ans = Math.max(ans, j - i + 1);
			map.put(s.charAt(j), j + 1);//下标 + 1 代表 i 要移动的下个位置
		}
		return ans;
	}

	//编写一个函数来查找字符串数组中的最长公共前缀。
	//如果不存在公共前缀，返回空字符串 ""。
	//输入：strs = ["flower","flow","flight"]
	//输出："fl"
	public static String longestCommonPrefix(String[] strs) {
		if (strs.length == 0)
			return "";
		String prefix = strs[0]; // 保存结果
		// 遍历每一个字符串
		for (int i = 1; i < strs.length; i++) {
			// 找到上次得到的结果 prefix 和当前字符串的最长子串
			int minLen = Math.min(prefix.length(), strs[i].length());
			int j = 0;
			for (; j < minLen; j++) {
				if (prefix.charAt(j) != strs[i].charAt(j)) {
					break;
				}
			}
			prefix = prefix.substring(0, j);
		}
		return prefix;
	}

	//给定一组非负整数 nums，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。
	//注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数。
	//输入：nums = [10,2]
	//输出："210"
	public String largestNumber(int[] nums) {
		Integer[] n = new Integer[nums.length];
		for (int i = 0; i < nums.length; i++) {
			n[i] = nums[i];
		}
		Arrays.sort(n, (n1, n2) -> {
			String s1 = n1 + "" + n2;
			String s2 = n2 + "" + n1;
			//compareTo 方法
			//如果参数是一个按字典顺序排列等于该字符串的字符串，则返回值为0;
			//如果参数是按字典顺序大于此字符串的字符串，则返回值小于0;
			//如果参数是按字典顺序小于此字符串的字符串，则返回值大于0。
			return s2.compareTo(s1);
		});
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nums.length; i++) {
			sb.append(n[i]);
		}
		String res = sb.toString();
		return res.charAt(0) == '0' ? "0" : res;
	}

	//给你两个字符串数组 words1 和 words2 ，请你返回在两个字符串数组中 都恰好出现一次 的字符串的数目。
	public static int countWords(String[] words1, String[] words2) {
		Map<String, Integer> map = new HashMap<>(8);
		for (String s : words1) {
			if (map.containsKey(s)) {
				map.put(s, 2);
			} else {
				map.put(s, 0);
			}
		}
		for (String s : words2) {
			if (map.containsKey(s)) {
				map.put(s, map.get(s) + 1);
			}
		}
		return Math.toIntExact(map.values().stream().filter(item -> item == 1).count());
	}

	//给你一个整数 money ，表示你总共有的钱数（单位为美元）和另一个整数 children ，表示你要将钱分配给多少个儿童。
//你需要按照如下规则分配：
//所有的钱都必须被分配。
//每个儿童至少获得 1 美元。
//没有人获得 4 美元。
//请你按照上述规则分配金钱，并返回 最多 有多少个儿童获得 恰好 8 美元。如果没有任何分配方案，返回 -1 。
	public static int distMoney(int money, int children) {
		if (children > money) {
			return -1;
		}
		// children=6  money = 20   8 8 1 1 1 1
		int temp = children * 8 - money;
		if (temp == 4) {
			return children - 2;
		}
		if (temp < 0) {
			return children - 1;
		}
		return (money - children) / 7;
	}

	//给你一个正整数 n ，请你计算在 [1，n] 范围内能被 3、5、7 整除的所有整数之和。
	//返回一个整数，用于表示给定范围内所有满足约束条件的数字之和。
	//示例 1：
	//输入：n = 7
	//输出：21
	//解释：在 [1, 7] 范围内能被 3、5、7 整除的所有整数分别是 3、5、6、7 。数字之和为 21。
	//10
	public static int sumOfMultiples(int n) {
		int result = 0;
		for (int i = 0; i < n + 1; i++) {
			if (i % 3 == 0 || i % 5 == 0 || i % 7 == 0) {
				result = result + i;
			}
		}
		return result;
	}

	//递归相加
	public static long recursionAdd(long n) {
		if (n == 0 || n == 1) {
			return n;
		} else {
			//5+4+3+2+1
			return n + recursionAdd(n - 1);
		}
	}

	// 递归平方，负平方
	public static double myPow(double x, int n) {
		if (n == 1) {
			return x;
		} else if (n == 0) {
			return 1;
		} else if (n < 0) {
			return 1 / myPow(x, -n);
		} else {
			return x * myPow(x, n - 1);
		}
	}

	public static void main(String[] args) {
		TimeInterval timeInterval = DateUtil.timer();
		System.out.println(myPow(23, -2));
		System.out.println("hs" + timeInterval.interval());
	}
}
