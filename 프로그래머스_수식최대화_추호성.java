import java.util.*;

public class Solution_수식최대화_추호성 {
	static long max = 0;

	static Stack<Long> ReturnStack(int op, Stack<Long> MS) {
		Stack<Long> SS = new Stack<>();

		while (MS.size() > 1) {
			long a = MS.pop();
			long b = MS.pop();

			if (b == op) {
				if (op == -1)
					MS.add(a + MS.pop());

				else if (op == -2)
					MS.add(a - MS.pop());

				else if (op == -3)
					MS.add(a * MS.pop());
			}

			else {
				SS.add(a);
				SS.add(b);
			}
		}

		while (!MS.isEmpty())
			SS.add(MS.pop());

		while (!SS.isEmpty())
			MS.add(SS.pop());

		return MS;
	}

	static void perm(List<Integer> arr, int[] output, boolean[] v1, int d, int n, int r, ArrayList<Long> arr2) {
		if (d == r) {
			Stack<Long> MS = new Stack<>();

			for (int i = 0; i < arr2.size(); i++)
				MS.add(arr2.get(arr2.size() - i - 1));

			for (int i = 0; i < output.length; i++) {
				MS = ReturnStack(output[i], MS);
			}

			max = Math.max(max, Math.abs(MS.pop()));
			return;
		}

		for (int i = 0; i < n; i++) {
			if (v1[i] != true) {
				v1[i] = true;
				output[d] = arr.get(i);
				perm(arr, output, v1, d + 1, n, r, arr2);
				v1[i] = false;
				;
			}
		}
	}

	static public long solution(String expression) {
		ArrayList<Long> arr = new ArrayList<>();
		ArrayList<Integer> newop = new ArrayList<>();

		int idx = 0;
		boolean[] oper = new boolean[3];

		for (int i = 0; i < expression.length(); i++) {
			if (expression.charAt(i) == '+') {
				arr.add(Long.parseLong(expression.substring(idx, i)));
				oper[0] = true;
				arr.add((long)-1);

				idx = i + 1;
			}

			else if (expression.charAt(i) == '-') {
				arr.add(Long.parseLong(expression.substring(idx, i)));
				oper[1] = true;
				arr.add((long)-2);

				idx = i + 1;
			}

			else if (expression.charAt(i) == '*') {
				arr.add(Long.parseLong(expression.substring(idx, i)));
				oper[2] = true;
				arr.add((long)-3);

				idx = i + 1;

			}
		}

		arr.add(Long.parseLong(expression.substring(idx)));

		//
		for (int i = 0; i < 3; i++)
			if (oper[i])
				newop.add(-1 - i);

		int SIZE = newop.size();
		perm(newop, new int[SIZE], new boolean[SIZE], 0, SIZE, SIZE, arr);

		return max;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(solution("100-200*300-500+20"));
	}

}
