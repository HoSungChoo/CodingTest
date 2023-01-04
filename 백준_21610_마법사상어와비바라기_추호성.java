// 32:11

import java.io.*;
import java.util.*;

public class 마법사상어와비바라기_추호성 {
	static final int[] dx = {0, 0, -1, -1, -1, 0, 1, 1, 1 };
	static final int[] dy = {0, -1, -1, 0, 1, 1, 1, 0, -1 };
	
	public static int MOD(int num, int N) {
		int obj = num;
		if (obj < 0) {
			while(obj < N)
				obj += N;
			
			return obj - N;
		}
		
		return obj % N;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader("C:/SSAFY/algo_study_private2/Study22/src/input"));
		// BufferedReader bf = new BufferedReader((new InputStreamReader(System.in)));
		StringTokenizer st = new StringTokenizer(bf.readLine(), " ");

		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		int[][] map = new int[N][N];
		int[][] ord = new int[M][2];
		boolean[][] v2 = new boolean[N][N];
		
		List<int[]> cloud = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(bf.readLine(), " ");
			
			for (int j = 0; j < N; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(bf.readLine(), " ");
			
			ord[i][0] = Integer.parseInt(st.nextToken());
			ord[i][1] = Integer.parseInt(st.nextToken());
		}
		
		cloud.add(new int[] {N - 1, 0});
		cloud.add(new int[] {N - 1, 1});
		cloud.add(new int[] {N - 2, 0});
		cloud.add(new int[] {N - 2, 1});
		
		//
		for (int tc = 0; tc < M; tc++) {
			int size = cloud.size();
			v2 = new boolean[N][N];
			
			// 1. move cloud
			for (int i = 0; i < cloud.size(); i++) {
				cloud.get(i)[0] = MOD(cloud.get(i)[0] + (dx[ord[tc][0]] * ord[tc][1]), N);
				cloud.get(i)[1] = MOD(cloud.get(i)[1] + (dy[ord[tc][0]] * ord[tc][1]), N);
			}
			
			// 2. add water
			for (int i = 0; i < cloud.size(); i++) {
				map[cloud.get(i)[0]][cloud.get(i)[1]] += 1;
			}
			
			// 4. add water
			int[] add = new int[size];
			int[] dis = {2, 4, 6, 8};
			
			for (int i = 0; i < size; i++) {
				for (int a = 0; a < dis.length; a++) {
					int px = cloud.get(i)[0] + dx[dis[a]];
					int py = cloud.get(i)[1] + dy[dis[a]];
					
					if (0 <= px && px < N && 0 <= py && py < N && map[px][py] > 0) {
						add[i] += 1;
					}
				}
			}
			
			for (int i = 0; i < size; i++) {
				map[cloud.get(i)[0]][cloud.get(i)[1]] += add[i];
				v2[cloud.get(i)[0]][cloud.get(i)[1]] = true;
			}
			
			// 3. delete exist cloud
			cloud.clear();
			
			// 5. add new cloud
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (!v2[i][j] && map[i][j] >= 2) {
						cloud.add(new int[] {i, j});
						map[i][j] -= 2;
					}
				}
			}
		}
		int result = 0;
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				result += map[i][j];
			}
		}
		
		System.out.println(result);
	}

}
