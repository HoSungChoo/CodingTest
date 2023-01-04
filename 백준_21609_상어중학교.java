import java.io.*;
import java.util.*;

public class Main {
	public static class Block {
		int x, y;
		int obj;
		int cnt;

		public Block(int x, int y, int obj, int cnt) {
			super();
			this.x = x;
			this.y = y;
			this.obj = obj;
			this.cnt = cnt;
		}

	}

	public static void gravity(int[][] map, int N) {
		int[][] smap = new int[N][N];

		for (int i = 0; i < N; i++)
			Arrays.fill(smap[i], -2);

		for (int j = 0; j < N; j++) {

			int d = N - 1;

			for (int i = N - 1; i >= 0; i--) {
				if (map[i][j] == -1) {
					smap[i][j] = -1;
					d = i - 1;
				}

				else if (map[i][j] > -1) {
					smap[d][j] = map[i][j];
					d -= 1;
				}
			}
		}

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				map[i][j] = smap[i][j];
	}

	public static void PRINT(int[][] map, int N) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] == -2)
					System.out.print("S ");

				else if (map[i][j] == -1)
					System.out.print("X ");

				else
					System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void rotate(int[][] map, int N) {
		int[][] smap = new int[N][N];

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				smap[N - 1 - j][i] = map[i][j];

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				map[i][j] = smap[i][j];
	}

	public static void main(String[] args) throws Exception {
		final int[] dx = { -1, 0, 1, 0 };
		final int[] dy = { 0, -1, 0, 1 };

		BufferedReader bf = new BufferedReader((new InputStreamReader(System.in)));
		StringTokenizer st = new StringTokenizer(bf.readLine(), " ");

		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		int[][] map = new int[N][N];
		int score = 0;

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(bf.readLine(), " ");

			for (int j = 0; j < N; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}

		//
//		PRINT(map, N);

		while (true) {
//			System.out.println("start");
			boolean[][] v2 = new boolean[N][N];
			int mx = -1, my = -1, mobj = -2;

			Queue<int[]> q = new ArrayDeque<>();
			List<int[]> mpos = new ArrayList<int[]>();

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (map[i][j] > 0 && !v2[i][j]) {
						int obj = map[i][j];
						List<int[]> pos = new ArrayList<int[]>();

						q.offer(new int[] { i, j });
						pos.add(new int[] { i, j });
						v2[i][j] = true;

						while (!q.isEmpty()) {
							int[] pk = q.poll();

							for (int a = 0; a < 4; a++) {
								int px = pk[0] + dx[a];
								int py = pk[1] + dy[a];

								if (0 <= px && px < N && 0 <= py && py < N && !v2[px][py]
										&& (map[px][py] == obj || map[px][py] == 0)) {
									q.offer(new int[] { px, py });
									pos.add(new int[] { px, py });
									v2[px][py] = true;
								}
							}
						}

						if (pos.size() == 1)
							v2[i][j] = false;

						else {
							// find standard block
							int sx = 21, sy = 21;

							for (int k = 0; k < pos.size(); k++) {
								int pgx = pos.get(k)[0];
								int pgy = pos.get(k)[1];

								if (map[pgx][pgy] == 0) {
									v2[pgx][pgy] = false;
									continue;
								}

								if (pgx < sx) {
									sx = pgx;
									sy = pgy;
								}

								else if (pgx == sx && pgy < sy) {
									sx = pgx;
									sy = pgy;
								}

								if (map[pgx][pgy] == 0)
									v2[pgx][pgy] = false;
							}

							// compare
							int flag = 0;

							if (mpos.size() < pos.size())
								flag = 1;

							else if (mpos.size() == pos.size()) {
								// rainbow
								int rmpos = 0;
								int rpos = 0;

								for (int k = 0; k < mpos.size(); k++)
									if (map[mpos.get(k)[0]][mpos.get(k)[1]] == 0)
										rmpos += 1;

								for (int k = 0; k < pos.size(); k++)
									if (map[pos.get(k)[0]][pos.get(k)[1]] == 0)
										rpos += 1;

								if (rmpos < rpos)
									flag = 1;

								else if (rmpos == rpos) {
									if (sx > mx)
										flag = 1;

									else if (sx == mx)
										if (sy > my)
											flag = 1;
								}
							}

							if (flag == 1) {
								mx = sx;
								my = sy;
								mobj = obj;
								mpos.clear();

								for (int k = 0; k < pos.size(); k++)
									mpos.add(pos.get(k));
							}
						}
					}
				}
			}

			if (mx == -1)
				break;

			// count score and delete
			score += (mpos.size() * mpos.size());

			for (int i = 0; i < mpos.size(); i++)
				map[mpos.get(i)[0]][mpos.get(i)[1]] = -2;

			// PRINT(map, N);
			// gravity
			// System.out.println("gravity");
			gravity(map, N);

			// PRINT(map, N);

			// rotate
			// System.out.println("rotate");
			rotate(map, N);

			// PRINT(map, N);

			// gravity
			// System.out.println("gravity");
			gravity(map, N);

			// PRINT(map, N);

		}
		System.out.println(score);
	}

}