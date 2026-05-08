import java.util.Optional;

public class MemoryManager {
  private final int pageSize;

  private int[][] memory;

/**
 * Constructs a new MemoryManager object with the specified number of pages in memory.
 * @param pageNum the number of pages in memory
 * @param pageSize the size of each page in memory
 */
  public MemoryManager(int pageNum, int pageSize) {
    this.pageSize = pageSize;
    memory = new int[pageNum][pageSize];
  }

/**
 * Attempts to allocate memory to a process with the specified process ID and size. If allocation is successful, returns a list of pages allocated to the process.
 * @param pid The process ID of the process being allocated memory.
 * @param size The memory required by the process.
 * @return An Optional containing the page numbers of pages allocated to the process on success, otherwise an empty Optional.
 */
  public Optional<Integer[]> allocate(int pid, int size) {
    int pagesNeeded = (int) Math.ceil((double) size / pageSize);
    Integer[] allocatedPages = new Integer[pagesNeeded];
    
    int currentPage = 0;
    while(currentPage < memory.length && pagesNeeded > 0) {
      // check if page is used
      if (memory[currentPage][0] == 0) {
        allocatedPages[allocatedPages.length - pagesNeeded] = currentPage;
        pagesNeeded--;

        // fill page, until either page is full or process is fully allocated
        for (int i = 0; i < pageSize && size > 0; ++i) {
          memory[currentPage][i] = pid;
          size--;
        }
      }
      currentPage++;
    }

    if(pagesNeeded == 0) {
      return Optional.of(allocatedPages);
    } else {
      // If process cannot fit, deallocate any memory that was attempted to be allocated
      for(Integer page : allocatedPages) {
        if(page != null) {
          for (int i = 0; i < pageSize; ++i) {
            memory[page][i] = 0;
          }
        }
      }
    }

    return Optional.empty();
  }

/**
 * Frees all memory allocated to the process with the given process ID.
 * @param pid The process ID of the process to clear from memory.
 */
  public void free(int pid) {
    for (int i = 0; i < memory.length; ++i) {
      if(memory[i][0] == pid) {
        for(int j = 0; j < pageSize; ++j) {
          memory[i][j] = 0;
        }
      }
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int pagesPerRow = 5; // Adjust this value to change how many pages are printed per row
    for (int i = 0; i < memory.length; ++i) {
      for (int j = 0; j < memory[i].length; ++j) {
        sb.append(memory[i][j]).append(" ");
      }
      if ((i + 1) % pagesPerRow == 0) {
        sb.append("\n");
      } else if (i != memory.length - 1) {
      sb.append("   ");
      }
    }
    return sb.toString();
  }
}