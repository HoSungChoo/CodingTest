import java.io.*;
import java.util.*;

public class Main_백준_11049_행렬곱셈순서_추호성 { // 01:10:00
	public static void main(String[] args) throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader("C:/SSAFY/algo_study_private2/Study22/src/input"));
		// BufferedReader bf = new BufferedReader((new InputStreamReader(System.in)));
		StringTokenizer st = new StringTokenizer(bf.readLine(), " ");

		int N = Integer.parseInt(st.nextToken());
		int[][] arr = new int[N][2];
		long[][] DP = new long[N][N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(bf.readLine(), " ");

			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());

		}
		//

		for (int i = 0; i < N - 1; i++)
			DP[i][i + 1] = arr[i][0] * arr[i][1] * arr[i + 1][1];

		for (int j = 2; j < N; j++) {
			for (int i = 0; i + j < N; i++) {
				long min = Integer.MAX_VALUE;
				int IJ = i + j;

				// k = i
				min = Math.min(min, arr[i][0] * arr[i][1] * arr[IJ][1] + DP[i + 1][IJ]);

				// k = j - 1
				min = Math.min(min, arr[i][0] * arr[IJ][0] * arr[IJ][1] + DP[i][IJ - 1]);

				// k = other
				for (int k = i + 1; k < IJ - 1; k++) 
					min = Math.min(min, DP[i][k] + DP[k + 1][IJ] + arr[i][0] * arr[k][1] * arr[IJ][1]);

				DP[i][IJ] = min;
			}
		}
		
		
		System.out.println(DP[0][N - 1]);
	}

}
