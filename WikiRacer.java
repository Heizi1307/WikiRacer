
/*
* Name: Longxin Li
* Date: 4/19/2021
* Filename: WikiRacer.java
* Course: CSC 210
* 
* Purpose: This assignment finds the Wiki link ladder between two pages.
 * and store in a priority queue.
*/

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Iterator;

public class WikiRacer {

	/*
	 * Do not edit this main function
	 */
	public static void main(String[] args) {
		List<String> ladder = findWikiLadder(args[0], args[1]);
		System.out.println(ladder);
	}

	private static void handle(Set<String> flagSet, Set<String> cSet, LinkVO cData, Set<String> fSet, MaxPQ maxPQ) {
		Set<String> set1 = new HashSet<>();
		Iterator<String> iter = cSet.iterator();
		while (iter.hasNext()) {
			String url = iter.next();
			boolean flag = flagSet.contains(url);
			if (!flag) {
				set1.add(url);
				flagSet.add(url);
			}
		}

		set1.parallelStream().forEach(url -> {
			Set<String> linkSet = WikiScraper.findWikiLinks(url);
			LinkVO vo = new LinkVO();
			List<String> list1 = vo.list;
			list1.addAll(cData.list);
			list1.add(url);
			vo.num = Utils.count(linkSet, fSet);

			maxPQ.enqueue(vo);
		});
	}

	/*
	 * Do not edit the method signature/header of this function TODO: Fill this
	 * function in.
	 */
	private static List<String> findWikiLadder(String start, String end) {
		MaxPQ maxPQ = createMaxPQ(start);
		Set<String> flagSet = new HashSet<>();

		Set<String> fSet = WikiScraper.findWikiLinks(end);
		for (; !maxPQ.isEmpty();) {
			LinkVO vo = maxPQ.dequeue();
			int sizeOfList = vo.list.size();
			String cUrl = vo.list.get(sizeOfList - 1);
			Set<String> cSet = WikiScraper.findWikiLinks(cUrl);
			flagSet.add(cUrl);

			boolean flag = cSet.contains(end);
			if (flag) {
				vo.list.add(end);
				return new ArrayList<>(vo.list);
			} else {
				handle(flagSet, cSet, vo, fSet, maxPQ);
			}
		}

		return new ArrayList<>();
	}

	private static MaxPQ createMaxPQ(String start) {
		LinkVO vo = new LinkVO();
		vo.list.add(start);
		MaxPQ maxPQ = new MaxPQ();
		maxPQ.enqueue(vo);

		return maxPQ;
	}

}

class Utils {

	public static int count(Set<String> setA, Set<String> setB) {
		int num = 0;
		for (String s : setA) {
			boolean flag = setB.contains(s);
			if (flag) {
				num++;
			}
		}

		return num;
	}
}

class LinkVO implements Comparable<LinkVO> {

	public LinkVO() {
		super();
		list = new ArrayList<>();
		this.num = 0;
	}

	public int compareTo(LinkVO other) {
		return num - other.num;
	}

	public List<String> list;
	public int num;
}
