/**
 * 
 */
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Queue;

/**
 * @author Shweta
 *
 */
enum Type {
	ATIME, MTIME, CTIME, CURRDIR, PARDIR, LINKCOUNT, INDIRECT, LOCATION
}

class Directory {

	int loc;
	int parent;

	Directory(int location, int par) {
		loc = location;
		parent = par;
	}
}

public class csefsck {

	final static int DEVICEID = 20;
	final static int MAXBLOCK = 10000;
	final static int MAXPOINTERS = 400;
	final static int BLOCKSIZE = 4096;
	static boolean[] isUsedList = new boolean[MAXBLOCK];

	// Initialize File System Checker
	public static void wakeUpFileSystemChecker(String path) {
		System.out.println("\nBeginning the system scan."
				+ "This process will take some time.");

		// Method to run all 7 checks of this this file system checker
		run_all_passes(path);
	}

	public static void run_all_passes(String path) {

		// PASS 1 - Check for DeviceID
		System.out.println("\nPASS 1 : Checking Device ID\n");
		scanSuperBlock(path, 1);
		System.out
				.println("Check for DeviceID is complete.Proceeding to next pass.");

		// PASS 2 - Check for timestamps
		System.out.println("\nPASS 2 : Checking timestamps\n");
		scanSuperBlock(path, 2);
		scanAll(path, 2);
		System.out.println("All timestamps are correct.");
		System.out
				.println("Check for timestamps is complete.Proceeding to next pass.");

		// PASS 3 - Check for free block list
		System.out.println("\nPASS 3 : Checking free block lists\n");

		if (checkFreeBlocklist(path)) {
			System.out
					.println("Check for free block list is complete.Proceeding to next pass.");
		}

		// PASS 4 - Check for current and parent directory blocknos
		System.out.println("\nPASS 4 : Checking "
				+ "current and parent block in all directories \n");
		scanAll(path, 4);
		System.out
				.println("Check for current and parent block is complete.Proceeding to next pass.");

		// PASS 5 - Check for Linkcount
		System.out.println("\nPASS 5 : Checking linkcount of directories");
		scanAll(path, 5);
		System.out
				.println("\nCheck for Linkcount is complete.Proceeding to next pass.");

		// PASS 6 - Check for Indexblock of files
		System.out.println("\nPASS 6 : Checking index block of files\n");
		scanAll(path, 6);
		System.out
				.println("Check for index block is complete.Proceeding to next pass.");

		// PASS 7 - Check for Size of file
		System.out.println("\nPASS 7 : Checking size of files\n");
		scanAll(path, 7);
		System.out
				.println("Size of all files is correct. No further defragmentation is not possible");

		System.out.println("\nThe system has been scanned successfully");
	}

	// Utility method to test if a string can parse Integer
	static public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// This will scan SuperBlock for DeviceID and Creation time
	public static void scanSuperBlock(String path, int checkno) {

		String line = null;
		BufferedReader buffereader = null;
		int devID = 0;
		try {
			buffereader = new BufferedReader(new FileReader(path
					+ "/fusedata.0"));
			line = buffereader.readLine();
			StringTokenizer st = new StringTokenizer(line, ",");
			String creationTimeBlock = st.nextToken(); // creation time
			st.nextToken();
			String devIdBlock = st.nextToken(); // Device ID
			String tokens[] = devIdBlock.split(":");
			devID = Integer.parseInt(tokens[1].trim());
			buffereader.close();

			if (checkno == 1) {

				if (checkDeviceID(devID)) {

					System.out.println("Device ID is correct.");
				} else {

					System.out.println("Device ID is incorrect."
							+ "This is not the desired FILE SYSTEM");
					System.out.println("Exiting File System Checker....");
					System.exit(0);
				}
			} else if (checkno == 2) {

				if (checkTime(creationTimeBlock)) {

					System.out.println("Creation time of superblock is "
							+ "correct." + "Proceeding to other directories");

				} else {
					System.out.println("Creation time of superblock is "
							+ "incorrect." + "Correcting the time.....");

					updateSuperBlockTime(path, line);
					System.out
							.println("Time corrected.Proceeding to other directories");

				}
			}

		} catch (FileNotFoundException ex) {

			System.out.println("\n Unable to open file fusedata.0'");
		} catch (IOException ex) {

			System.out.println("\n Error reading file fusedata.0 ");
		}

	}

	// Check if deviceID is 20. If not, return false.
	public static boolean checkDeviceID(int devID) {
		if (devID == DEVICEID)
			return true;
		else
			return false;
	}

	// This method will check if the time passed is
	// greater or leass than the current time.
	public static boolean checkTime(String timeBlock) {

		long timeVal = 0;
		String token[] = timeBlock.split(":");
		timeVal = Long.parseLong(token[1].trim());
		if (timeVal < System.currentTimeMillis()) {
			return true;
		} else {
			return false;
		}
	}

	// This method updates Super Block Creation Time to current time in seconds
	public static void updateSuperBlockTime(String path, String fileline) {

		StringTokenizer st = new StringTokenizer(fileline, ",");
		String creatTimeBlock = st.nextToken().trim(); // creation time
		String mountIDBlock = st.nextToken().trim(); // number of mounts
		String devIDBlock = st.nextToken().trim();
		String freeStartBlock = st.nextToken().trim();
		String freeEndBlock = st.nextToken().trim();
		String rootBlock = st.nextToken().trim();
		String maxBlock = st.nextToken().trim();
		FileWriter fstream;
		long creatTime = 0;
		String tokens[] = creatTimeBlock.split(":");
		creatTime = Long.parseLong(tokens[1].trim());
		creatTime = System.currentTimeMillis() / 1000;

		try {
			fstream = new FileWriter(path + "/fusedata.0");
			BufferedWriter fbw = new BufferedWriter(fstream);
			fbw.write("{creationTime: " + creatTime + "," + mountIDBlock + ","
					+ devIDBlock + "," + freeStartBlock + "," + freeEndBlock
					+ "," + rootBlock + "," + maxBlock);
			fbw.newLine();
			fbw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * This method checks if free block list is accurate. Display errors of
	 * inaccurate free blocks
	 */
	public static boolean checkFreeBlocklist(String path) {

		BufferedReader buffereader = null;
		String line = null;
		int blockno = 0;
		TreeSet<Integer> hs = new TreeSet<Integer>();
		int firstfile = (MAXBLOCK / 400) + 2;
		boolean errorflag = false;
		int j = 0;

		try {
			isUsedList[0] = true;
			for (int i = 1; i <= MAXBLOCK / MAXPOINTERS; i++) {

				buffereader = new BufferedReader(new FileReader(path
						+ "/fusedata." + i));
				line = buffereader.readLine();
				StringTokenizer st = new StringTokenizer(line, ",");
				while (st.hasMoreTokens()) {
					blockno = Integer.parseInt(st.nextToken().trim()
							.substring(0));
					hs.add(blockno);
				}
				isUsedList[i] = true;
				buffereader.close();
			}
			scanAll(path, 3);
			for (j = firstfile; j < MAXBLOCK; j++) {

				if (!isUsedList[j] && !hs.contains(j)) {
					System.out.println("ERROR: Freeblock " + j
							+ " is not present in the freeblock "
							+ (int) (Math.floor(j / 400) + 1));
					hs.add(j);
					errorflag = true;
				} else if (isUsedList[j] && hs.contains(j)) {
					System.out.println("ERROR: " + "Block " + j
							+ " is not empty but present in the freeblock list"
							+ (int) (Math.floor(j / 400) + 1));
					hs.remove(j);// update FreeBlock source to remove referenced
									// block
					errorflag = true;
				}
			}
			if (errorflag) {
				System.out.println("\nCorrecting free blocklist....");
				updateFreeBlockList(path);
				System.out.println("Free blocklist corrected.");
				return true;
			} else {
				System.out.println("No errors found in free blocklist.");
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * This method updates Free block list everytime the block is freed or for
	 * check 2
	 */

	public static void updateFreeBlockList(String path) {

		FileWriter fstream;
		BufferedWriter fbw;
		int loopCounter = (MAXBLOCK / 400) + 2;
		int firstFreeBlockNo = loopCounter;
		int j = 0;

		try {
			for (int i = 1; i <= MAXBLOCK / MAXPOINTERS; i++) {
				fstream = new FileWriter(path + "/fusedata." + i);
				fbw = new BufferedWriter(fstream);
				// First block is a special case
				if (i == 1) {
					for (j = loopCounter; j < MAXPOINTERS; j++) {
						if (!isUsedList[firstFreeBlockNo]) {
							if (j == MAXPOINTERS - 1) {
								fbw.write(firstFreeBlockNo + "");
							} else {
								fbw.write(firstFreeBlockNo + ",");
							}
						}
						firstFreeBlockNo++;
					}
					fbw.close();
				} else {
					for (j = 0; j < MAXPOINTERS; j++) {
						if (!isUsedList[firstFreeBlockNo]) {
							if (j == MAXPOINTERS - 1) {
								fbw.write(firstFreeBlockNo + "");
							} else {
								fbw.write(firstFreeBlockNo + ",");
							}
						}
						firstFreeBlockNo++;
					}
					fbw.close();
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * This method is the crust of the File System Checker. It is called for
	 * every check to scan Directory and Files and perform according to the
	 * check required.
	 */
	public static void scanAll(String path, int checkno) {

		int rootblock = (MAXBLOCK / 400) + 1;
		BufferedReader buffereader = null;
		LinkedList<Directory> dirqueue = new LinkedList<Directory>();
		String line = null;
		String nextBlock = null;
		String type = null;
		int location = 0;
		int calclinkcount = 0;
		try {
			Directory dir = new Directory(rootblock, rootblock);
			dirqueue.add(dir);
			while (!dirqueue.isEmpty()) {
				Directory d = dirqueue.remove();
				if (checkno == 3) {
					isUsedList[d.loc] = true;
				}
				buffereader = new BufferedReader(new FileReader(path
						+ "/fusedata." + d.loc));
				line = buffereader.readLine();
				StringTokenizer st = new StringTokenizer(line, ",");
				st.nextToken(); // Size, not required
				st.nextToken(); // UI,not required
				st.nextToken(); // GID, not required
				st.nextToken(); // Mode,not required
				String atimeBlock = st.nextToken().trim();
				String ctimeBlock = st.nextToken().trim();
				String mtimeBlock = st.nextToken().trim();
				String linkcountBlock = st.nextToken().trim();
				String finoBlock = st.nextToken().trim();
				boolean firstchild = true;
				calclinkcount = 0;
				while (st.hasMoreTokens()) {
					StringTokenizer stInner = null;
					if (firstchild) {
						nextBlock = finoBlock;
						stInner = new StringTokenizer(nextBlock, ":");
						stInner.nextToken();
						type = stInner.nextToken().trim().substring(1);
						firstchild = false;
					} else {
						nextBlock = st.nextToken();
						stInner = new StringTokenizer(nextBlock, ":");
						type = stInner.nextToken().trim().substring(0);
					}
					calclinkcount++;
					String name = stInner.nextToken();
					if (st.countTokens() == 0) {
						location = Integer.parseInt(stInner.nextToken("}")
								.substring(1));
					} else {
						location = Integer.parseInt(stInner.nextToken());
					}
					if (type.equals("d")) {

						/*
						 * If the Type is directory, scan the directory for
						 * checks 2,3,4 and 5
						 */
						scanDirectories(path, buffereader, name, d, checkno,
								location, line, dirqueue);
					} else if (type.equals("f")) {

						/*
						 * If the Type is Files, scan the file for checks 2,3,6
						 * and 7
						 */
						scanFiles(path, d, checkno, location, line);
					}

				}
				// For timestamps
				if (checkno == 2 && type.equals("d")) {

					if (!checkTime(atimeBlock)) {
						System.out.println("\nAccess time of directory "
								+ d.loc + " is incorrect."
								+ "\nCorrecting the time");
						buffereader.close();
						buffereader = new BufferedReader(new FileReader(path
								+ "/fusedata." + d.loc));
						line = buffereader.readLine();
						if (updateDirectory(path, line, d.loc, 0, Type.ATIME)) {
							System.out.println("Access time corrected");
						}

					}
					if (!checkTime(ctimeBlock)) {

						System.out.println("\nCreation time of directory "
								+ d.loc + " is incorrect."
								+ "\nCorrecting the time.");
						buffereader.close();
						buffereader = new BufferedReader(new FileReader(path
								+ "/fusedata." + d.loc));
						line = buffereader.readLine();
						if (updateDirectory(path, line, d.loc, 0, Type.CTIME)) {
							System.out.println("Creation time corrected.");
						}

					}
					if (!checkTime(mtimeBlock)) {
						System.out.println("\nModification time of directory "
								+ d.loc + " is incorrect."
								+ "Correcting the time.....");
						buffereader.close();
						buffereader = new BufferedReader(new FileReader(path
								+ "/fusedata." + d.loc));
						line = buffereader.readLine();
						if (updateDirectory(path, line, d.loc, 0, Type.MTIME)) {
							System.out.println("Modification time corrected");

						}
					}
				}
				// For Check 5 - Linkcount of Directories
				if (type.equals("d") && checkno == 5) {
					String tokens[] = linkcountBlock.split(":");
					int dirlinkcount = Integer.parseInt(tokens[1].trim());
					if (dirlinkcount != calclinkcount) {
						System.out.println("\nLinkcount of block no " + d.loc
								+ " is incorrect");
						System.out.println("Correcting Linkcount.....");
						if (updateDirectory(path, line, d.loc, calclinkcount,
								Type.LINKCOUNT)) {
							System.out.println("Linkcount corrected.");
						}
					}
				}
				buffereader.close();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* This method will scan each specific directory for check no 4 */
	public static void scanDirectories(String path, BufferedReader buffereader,
			String name, Directory d, int checkno, int location, String line,
			Queue<Directory> dirqueue) {

		try {
			switch (name) {
			case ".": // For Current Directory
				if (d.loc != location && checkno == 4) {
					System.out.println("Current directory of " + d.loc
							+ " is incorrectly stored as " + location);
					System.out.println("Correcting data.....");
					if (updateDirectory(path + "inner", line, d.loc, d.loc,
							Type.CURRDIR)) {
						System.out.println("Data corrected.\n");
					}

				}
				Thread.sleep(1000);
				break;
			case "..": // For Parent Directory
				if (d.parent != location && checkno == 4) {
					System.out.println("Parent directory of " + d.loc
							+ " is incorrectly stored as " + location);
					System.out.println("Correcting data.....");
					if (updateDirectory(path + "outer", line, d.loc, d.parent,
							Type.PARDIR)) {
						System.out.println("Data corrected.\n");
					}
				}
				break;
			default:
				Directory dirchild = new Directory(location, d.loc);
				dirqueue.add(dirchild);
				break;

			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/*
	 * This method will Scan all files. Used for check 2, 3, 6 and 7
	 */
	public static void scanFiles(String path, Directory d, int checkno,
			int location, String line) {

		BufferedReader fileReader = null;
		BufferedReader buffereader = null;
		String filedata = null;
		int[] iarray = new int[MAXPOINTERS];
		try {
			buffereader = new BufferedReader(new FileReader(path + "/fusedata."
					+ location));
			filedata = buffereader.readLine();
			StringTokenizer fileScanner = new StringTokenizer(filedata, ",");
			fileScanner.nextToken(); // Size, not required
			fileScanner.nextToken(); // UID, not required
			fileScanner.nextToken(); // GID, not required
			fileScanner.nextToken(); // Mode,not required
			fileScanner.nextToken(); // Linkcount Block, not required
			String fatimeBlock = fileScanner.nextToken();
			String fctimeBlock = fileScanner.nextToken();
			String fmtimeBlock = fileScanner.nextToken();
			String indexBlock = fileScanner.nextToken();
			if (checkno == 3) {
				isUsedList[location] = true;
			}
			// ----For TIMESTAMPS
			if (checkno == 2) {
				if (!checkTime(fatimeBlock)) {
					System.out.println("\nAccess time of file " + location
							+ " is incorrect." + "Correcting the time.....");
					buffereader.close();
					buffereader = new BufferedReader(new FileReader(path
							+ "/fusedata." + location));
					filedata = buffereader.readLine();
					if (updateFile(path, filedata, location, 0, Type.ATIME)) {
						System.out.println("Access Time corrected");
					}
				}
				if (!checkTime(fctimeBlock)) {
					System.out.println("\nCreation time of file " + location
							+ " is incorrect." + "Correcting the time.....");
					buffereader.close();
					buffereader = new BufferedReader(new FileReader(path
							+ "/fusedata." + location));
					filedata = buffereader.readLine();
					if (updateFile(path, filedata, location, 0, Type.CTIME)) {
						System.out.println("Creation Time corrected");
					}

				}
				if (!checkTime(fmtimeBlock)) {
					System.out.println("\nModification time of file "
							+ location + " is incorrect."
							+ "Correcting the time.....");
					buffereader.close();
					buffereader = new BufferedReader(new FileReader(path
							+ "/fusedata." + location));
					filedata = buffereader.readLine();
					if (updateFile(path, filedata, location, 0, Type.MTIME)) {
						System.out.println("Modification time corrected");
					}

				}

			} else if (checkno == 3) { // ----For Freeblock list
				String tokens[] = indexBlock.split(":");
				int flocation = Integer.parseInt(tokens[2].split("}")[0]);
				isUsedList[flocation] = true;
				fileReader = new BufferedReader(new FileReader(path
						+ "/fusedata." + flocation));
				String indexdata = fileReader.readLine();
				StringTokenizer arrayScanner = new StringTokenizer(indexdata,
						",");
				int i = 0;
				while (arrayScanner.hasMoreTokens()) {
					String nextToken = arrayScanner.nextToken().trim();
					if (isInteger(nextToken)) {
						iarray[i] = Integer.parseInt(nextToken);
						isUsedList[iarray[i]] = true;
						i++;
					}
				}
			} else if (checkno == 6) { // ---For Indirect Block
				String tokens[] = indexBlock.split(":");
				String tokenWithoutSpace = tokens[1].trim().substring(0, 1);
				int indirect = Integer.parseInt(tokenWithoutSpace);
				int flocation = Integer.parseInt(tokens[2].split("}")[0]);
				fileReader = new BufferedReader(new FileReader(path
						+ "/fusedata." + flocation));
				String indexdata = fileReader.readLine();
				StringTokenizer arrayScanner = new StringTokenizer(indexdata,
						",");
				int i = 0;
				int countTokens = 0;
				while (arrayScanner.hasMoreTokens()) {
					String nextToken = arrayScanner.nextToken().trim();
					if (isInteger(nextToken)) {
						iarray[i++] = Integer.parseInt(nextToken);
						countTokens++;
					}
				}
				/*
				 * CASE 1: If data contained in location pointer is array but
				 * indirect block is 0
				 */
				if (countTokens > 1 && indirect == 0) {
					System.out.println("Indirect of block " + location
							+ " is incorrect as 0");
					System.out.println("Changing indirect to 1.");
					if (updateFile(path, filedata, location, 1, Type.INDIRECT)) {
						System.out.println("Indirect block changed.");
					}

				}
				/*
				 * CASE 2: If data contained in location pointer is a single
				 * digit but indirect is 1. The indirect should be changed to 0
				 * and location should directly point to file data The indirect
				 * file should be then added to free block list
				 */
				else if (indirect == 1 && countTokens == 1) {

					System.out.println("Indirect of block " + location
							+ " is incorrect as 1");
					// No use of Indirecting.Can directly point to File data
					System.out.println("Changing indirect to 0 and "
							+ "location to actual file data.");
					if (updateFile(path, filedata, location, iarray[0],
							Type.LOCATION)) {
						System.out.println("Indirect and location changed.");
						// The indirect block can be now added to free block
						// list
						System.out.println("Updating free block list....");
						isUsedList[flocation] = false;
						updateFreeBlockList(path);
						System.out
								.println("Free block list updated with currently freed block");
					}
				}
				fileReader.close();

			}
			// ----For File size
			else if (checkno == 7) {
				long combinedSize = 0;
				String tokens[] = indexBlock.split(":");
				String tokenWithoutSpace = tokens[1].trim().substring(0, 1);
				int indirect = Integer.parseInt(tokenWithoutSpace);
				int flocation = Integer.parseInt(tokens[2].split("}")[0]);
				fileReader = new BufferedReader(new FileReader(path
						+ "/fusedata." + flocation));
				String indexdata = fileReader.readLine();
				if (indirect == 0) {
					Path p = Paths.get(path + "/fusedata." + flocation);
					byte[] data = Files.readAllBytes(p);
					if (data.length > BLOCKSIZE) {
						System.out
								.println("\nThe size of the file "
										+ flocation
										+ " is greater than the blocksize but indirect is 0");
					}
				} else if (indirect == 1) {

					StringTokenizer arrayScanner = new StringTokenizer(
							indexdata, ",");
					int i = 0;
					while (arrayScanner.hasMoreTokens()) {
						String nextToken = arrayScanner.nextToken().trim();
						if (isInteger(nextToken)) {
							System.out.println("nextToken" + nextToken);
							iarray[i++] = Integer.parseInt(nextToken);
						}
					}
					LinkedList<byte[]> fileData = new LinkedList<byte[]>();
					for (int j = 0; j < i; j++) {
						Path p = Paths.get(path + "/fusedata." + iarray[j]);
						byte[] data = Files.readAllBytes(p);
						fileData.add(data);
						combinedSize += data.length;

					}
					if (combinedSize < BLOCKSIZE * iarray.length) {
						if (combinedSize <= BLOCKSIZE * (iarray.length - 1)) {

							byte[] compArr = new byte[BLOCKSIZE * iarray.length
									- 1];
							int comIndex = 0;
							for (byte[] bAr : fileData) {
								for (byte b : bAr) {
									compArr[comIndex++] = b;
								}
							}
							int x = 0;
							int fileNum = 0;
							int bytesRemaining = comIndex;
							FileWriter fInd = new FileWriter(path
									+ "/fusedata." + flocation);
							BufferedWriter bInd = new BufferedWriter(fInd);
							while (x < comIndex) {
								FileOutputStream fos = new FileOutputStream(
										path + "/fusedata." + iarray[fileNum]);
								int bytesToCopy = 0;
								if (bytesRemaining > MAXBLOCK) {
									bytesToCopy = MAXBLOCK;
									bytesRemaining = bytesRemaining - MAXBLOCK;
								} else {
									bytesToCopy = bytesRemaining;
								}
								fos.write(Arrays.copyOfRange(compArr, x,
										bytesToCopy));
								System.out.println("x : " + x);
								System.out.println("iarrya : "
										+ iarray[fileNum]);
								System.out.println("Filenum : " + fileNum);
								x = x + MAXBLOCK;
								bInd.write(iarray[fileNum]+",");
								fileNum++;
								fos.close();
							}

							bInd.close();

							// here
						}
					}
				}
			}

			buffereader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static boolean updateDirectory(String path, String fileline,
			int loc, int correctVal, Type t) {

		StringTokenizer stUpdate = new StringTokenizer(fileline, ",");
		String sizeBlock = stUpdate.nextToken().trim();
		String uidBlock = stUpdate.nextToken().trim();
		String gidBlock = stUpdate.nextToken().trim();
		String modeBlock = stUpdate.nextToken().trim();
		String atimeBlock = stUpdate.nextToken().trim();
		String ctimeBlock = stUpdate.nextToken().trim();
		String mtimeBlock = stUpdate.nextToken().trim();
		String linkcountBlock = stUpdate.nextToken().trim();
		String finoBlock = stUpdate.nextToken().trim();
		FileWriter fstream;
		BufferedWriter fbw;
		long timeVal = 0;
		boolean firstchild = true;
		String nextBlock = null;
		String finoText = null;
		StringBuilder appendString = new StringBuilder();
		int location = 0;

		try {
			fstream = new FileWriter(path + "/fusedata." + loc);
			fbw = new BufferedWriter(fstream);
			if (t == Type.CURRDIR || t == Type.PARDIR) {
				while (stUpdate.hasMoreTokens()) {
					StringTokenizer stInner = null;
					if (firstchild) {
						nextBlock = finoBlock;
						stInner = new StringTokenizer(nextBlock, ":");
						finoText = stInner.nextToken().trim();
						stInner.nextToken();
					} else {
						nextBlock = stUpdate.nextToken().trim();
						stInner = new StringTokenizer(nextBlock, ":");
						stInner.nextToken();
					}
					String name = stInner.nextToken();
					if (stUpdate.countTokens() == 0) {
						location = Integer.parseInt(stInner.nextToken("}")
								.substring(1));
					} else {
						location = Integer.parseInt(stInner.nextToken());
					}
					location = correctVal;
					String restOfTheString = null;
					if (name.equals(".") && t == Type.CURRDIR) {
						if (firstchild) {
							while (stUpdate.hasMoreTokens()) {
								restOfTheString = stUpdate.nextToken().trim();
							}
							fbw.write(sizeBlock + "," + uidBlock + ","
									+ gidBlock + "," + modeBlock + ","
									+ atimeBlock + "," + ctimeBlock + ","
									+ mtimeBlock + "," + linkcountBlock + ","
									+ finoText + ":{d:" + name + ":" + location
									+ "," + restOfTheString);
							fbw.newLine();
							fbw.close();
							return true;
						} else {
							while (stUpdate.hasMoreTokens()) {
								restOfTheString = stUpdate.nextToken().trim();
							}
							fbw.write(sizeBlock + "," + uidBlock + ","
									+ gidBlock + "," + modeBlock + ","
									+ atimeBlock + "," + ctimeBlock + ","
									+ mtimeBlock + "," + linkcountBlock + ","
									+ appendString + "d:" + name + ":"
									+ location + "," + restOfTheString);
							fbw.newLine();
							fbw.close();
							return true;
						}
					}
					if (name.equals("..") && t == Type.PARDIR) {

						if (firstchild) {
							while (stUpdate.hasMoreTokens()) {
								restOfTheString = stUpdate.nextToken().trim();
							}
							fbw.write(sizeBlock + "," + uidBlock + ","
									+ gidBlock + "," + modeBlock + ","
									+ atimeBlock + "," + ctimeBlock + ","
									+ mtimeBlock + "," + linkcountBlock + ","
									+ finoText + ":{d:" + name + ":" + location
									+ "," + restOfTheString);
							fbw.newLine();
							fbw.close();
							return true;
						} else {
							while (stUpdate.hasMoreTokens()) {
								restOfTheString = stUpdate.nextToken().trim();
							}
							fbw.write(sizeBlock + "," + uidBlock + ","
									+ gidBlock + "," + modeBlock + ","
									+ atimeBlock + "," + ctimeBlock + ","
									+ mtimeBlock + "," + linkcountBlock + ","
									+ appendString + "d:" + name + ":"
									+ location + "," + restOfTheString);
							fbw.newLine();
							fbw.close();
							return true;
						}

					}
					firstchild = false;
					appendString.append(nextBlock);
					appendString.append(",");
				}

			} else if (t == Type.LINKCOUNT) {

				StringBuilder restOfTheString = new StringBuilder();
				while (stUpdate.hasMoreTokens()) {
					restOfTheString.append(stUpdate.nextToken().trim());
					if (!(stUpdate.countTokens() == 0))
						restOfTheString.append(",");
				}
				String token[] = linkcountBlock.split(":");
				String linkcountText = token[0];
				int linkcount = Integer.parseInt(token[1].trim());
				linkcount = correctVal;

				fbw.write(sizeBlock + "," + uidBlock + "," + gidBlock + ","
						+ modeBlock + "," + atimeBlock + "," + ctimeBlock + ","
						+ mtimeBlock + "," + linkcountText + ":" + linkcount
						+ "," + finoBlock + "," + restOfTheString);
				fbw.newLine();
				fbw.close();
				return true;

			} else if (t == Type.ATIME) {
				StringBuilder restOfTheString = new StringBuilder();
				while (stUpdate.hasMoreTokens()) {
					restOfTheString.append(stUpdate.nextToken().trim());
					if (!(stUpdate.countTokens() == 0))
						restOfTheString.append(",");
				}
				String token[] = atimeBlock.split(":");
				timeVal = Long.parseLong(token[1]);
				timeVal = System.currentTimeMillis() / 1000;
				fbw.write(sizeBlock + "," + uidBlock + "," + gidBlock + ","
						+ modeBlock + "," + token[0] + ":" + timeVal + ","
						+ ctimeBlock + "," + mtimeBlock + "," + linkcountBlock
						+ "," + finoBlock + "," + restOfTheString);
				fbw.newLine();
				fbw.close();
				return true;

			} else if (t == Type.CTIME) {
				StringBuilder restOfTheString = new StringBuilder();
				while (stUpdate.hasMoreTokens()) {
					restOfTheString.append(stUpdate.nextToken().trim());
					if (!(stUpdate.countTokens() == 0))
						restOfTheString.append(",");
				}
				String token[] = ctimeBlock.split(":");
				timeVal = Long.parseLong(token[1]);
				timeVal = System.currentTimeMillis() / 1000;
				fbw.write(sizeBlock + "," + uidBlock + "," + gidBlock + ","
						+ modeBlock + "," + atimeBlock + "," + token[0] + ":"
						+ timeVal + "," + mtimeBlock + "," + linkcountBlock
						+ "," + finoBlock + "," + restOfTheString);
				fbw.newLine();
				fbw.close();
				return true;

			} else {
				StringBuilder restOfTheString = new StringBuilder();
				while (stUpdate.hasMoreTokens()) {
					restOfTheString.append(stUpdate.nextToken().trim());
					if (!(stUpdate.countTokens() == 0))
						restOfTheString.append(",");
				}
				String token[] = mtimeBlock.split(":");
				timeVal = Long.parseLong(token[1]);
				timeVal = System.currentTimeMillis() / 1000;
				fbw.write(sizeBlock + "," + uidBlock + "," + gidBlock + ","
						+ modeBlock + "," + atimeBlock + "," + ctimeBlock + ","
						+ token[0] + ":" + timeVal + "," + linkcountBlock + ","
						+ finoBlock + "," + restOfTheString);
				fbw.newLine();
				fbw.close();
				return true;

			}
			fbw.newLine();
			fbw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public static boolean updateFile(String path, String fileline, int loc,
			int correctVal, Type t) {

		StringTokenizer st = new StringTokenizer(fileline, ",");
		String fsizeBlock = st.nextToken().trim();
		String fuidBlock = st.nextToken().trim();
		String fgidBlock = st.nextToken().trim();
		String fmodeBlock = st.nextToken().trim();
		String flinkcountBlock = st.nextToken().trim();
		String fatimeBlock = st.nextToken().trim();
		String fctimeBlock = st.nextToken().trim();
		String fmtimeBlock = st.nextToken().trim();
		String indexBlock = st.nextToken().trim();
		FileWriter fstream;
		BufferedWriter fbw;
		long timeVal = 0;

		try {
			fstream = new FileWriter(path + "/fusedata." + loc);
			fbw = new BufferedWriter(fstream);
			if (t == Type.INDIRECT) {
				String tokens[] = indexBlock.split(":");
				String tokenWithoutSpace = tokens[1].trim().substring(0, 1);
				String locText = tokens[1].trim().substring(1).trim();
				int indirect = Integer.parseInt(tokenWithoutSpace);
				indirect = correctVal;
				fbw.write(fsizeBlock + "," + fuidBlock + "," + fgidBlock + ","
						+ fmodeBlock + "," + flinkcountBlock + ","
						+ fatimeBlock + "," + fctimeBlock + "," + fmtimeBlock
						+ "," + tokens[0] + ":" + indirect + " " + locText
						+ ":" + tokens[2]);
				fbw.newLine();
				fbw.close();
				return true;

			} else if (t == Type.LOCATION) {
				String tokens[] = indexBlock.split(":");
				String tokenWithoutSpace = tokens[1].trim().substring(0, 1);
				String locText = tokens[1].trim().substring(1).trim();
				String locWithoutSpace[] = tokens[2].split("}");
				int indirect = Integer.parseInt(tokenWithoutSpace);
				int oldlocation = Integer.parseInt(locWithoutSpace[0]
						.substring(0));
				indirect = 0;
				oldlocation = correctVal;
				fbw.write(fsizeBlock + "," + fuidBlock + "," + fgidBlock + ","
						+ fmodeBlock + "," + flinkcountBlock + ","
						+ fatimeBlock + "," + fctimeBlock + "," + fmtimeBlock
						+ "," + tokens[0] + ":" + indirect + " " + locText
						+ ":" + oldlocation + "}");
				fbw.newLine();
				fbw.close();
				return true;

			} else if (t == Type.ATIME) {
				String token[] = fatimeBlock.split(":");
				timeVal = Long.parseLong(token[1]);
				timeVal = System.currentTimeMillis() / 1000;
				fbw.write(fsizeBlock + "," + fuidBlock + "," + fgidBlock + ","
						+ fmodeBlock + "," + flinkcountBlock + "," + token[0]
						+ ":" + timeVal + "," + fctimeBlock + "," + fmtimeBlock
						+ "," + indexBlock);
				fbw.newLine();
				fbw.close();
				return true;

			} else if (t == Type.CTIME) {
				String token[] = fctimeBlock.split(":");
				timeVal = Long.parseLong(token[1]);
				timeVal = System.currentTimeMillis() / 1000;
				fbw.write(fsizeBlock + "," + fuidBlock + "," + fgidBlock + ","
						+ fmodeBlock + "," + flinkcountBlock + ","
						+ fatimeBlock + "," + token[0] + ":" + timeVal + ","
						+ fmtimeBlock + "," + indexBlock);
				fbw.newLine();
				fbw.close();
				return true;

			} else if (t == Type.MTIME) {
				String token[] = fmtimeBlock.split(":");
				timeVal = Long.parseLong(token[1]);
				timeVal = System.currentTimeMillis() / 1000;
				fbw.write(fsizeBlock + "," + fuidBlock + "," + fgidBlock + ","
						+ fmodeBlock + "," + flinkcountBlock + ","
						+ fatimeBlock + "," + fctimeBlock + "," + token[0]
						+ ":" + timeVal + "," + indexBlock);
				fbw.newLine();
				fbw.close();
				return true;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Checking if the File System exists
		try {
			File f = new File("./fusedata");
			String path = f.getCanonicalPath();
			if (!f.exists()) {
				System.out.println("\nIncorrect path: NO FILE SYSTEM FOUND");
				System.out.println("\nfsck died");
				System.exit(0);
			}
			// Initiate File System Checker
			wakeUpFileSystemChecker(path);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
