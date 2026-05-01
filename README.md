# 📚 Library Management System — Java

**Group 4 | CSE-423 | Department of Computer Science and Engineering**

A Java-based Library Management System built in two versions.
Version 1 (main) was our starting point.
Version 2 (Updates) is the improved version where we applied SOLID Principles, fixed Code Smells, and introduced Design Patterns.

🔗 **GitHub main (Version 1):** https://github.com/yeasinkafi/Library-Management-System-Java-.git
🔗 **GitHub Updates (Version 2):** https://github.com/yeasinkafi/Library-Management-System-Java-/tree/Updates

---

## 👥 Group Members

| Name | ID |
|---|---|
| Yeasin Md Al Kafi | 2022-1-60-198 |
| Mayisha Hossain Bhuiyan | 2022-1-60-191 |
| Sadia Rahman Roshni | 2021-3-60-145 |
| Sumaiya Binte Faruq | 2019-2-60-052 |

---

## 📖 What This Project Does

This is a console-based library system where:

- **Admins** can add books, delete books, view all orders, confirm or reject orders, and manage users.
- **Normal Users** can search for books, borrow books, return books, place orders, and check their fine.

Version 1 worked correctly but had messy design.
Version 2 cleaned it up using proper software engineering principles.

---

## ✅ SOLID Principles

SOLID is a set of 5 design rules that make code easier to read, extend, and maintain.
Here is where we applied each one and why.

---

### S — Single Responsibility Principle
> *One class should have only one job.*

**The Problem in Version 1:**

The `Database` class was doing everything. It was reading files, writing files, parsing data, storing data in memory, AND handling business logic like confirming orders and borrowing books — all in one 500+ line class.

The `Admin` and `NormalUser` classes also had a mix of menu display, input handling, and their own utility methods.

The `CalculateFine` class was reaching into `Borrowing` objects and doing math that logically belonged to `Borrowing` itself.

**What We Fixed in Version 2:**

We broke the `Database` class into focused pieces:

| Class | Its Only Job |
|---|---|
| `FileBookRepository` | Reads and writes book data to files only |
| `FileUserRepository` | Reads and writes user data to files only |
| `FileOrderRepository` | Reads and writes order data to files only |
| `FileBorrowingRepository` | Reads and writes borrowing records only |
| `BookService` | Handles business logic for books |
| `OrderService` | Handles business logic for orders |
| `BorrowingService` | Handles business logic for borrowing/returning |
| `UserService` | Handles business logic for login/registration |
| `ConsoleIO` | Handles ALL keyboard input and output |

Fine calculation logic was moved INTO the `Borrowing` class itself. Now `Borrowing` knows its own dates and calculates its own fine. `CalculateFine` just asks the object — it no longer does the math externally.

---

### O — Open/Closed Principle
> *Add new features without changing existing code.*

**The Problem in Version 1:**

The `SearchBook` class had a chain of `if/else` statements to choose which field to search by. Adding a 4th search option meant going into that class and editing it directly.

Menu text was hardcoded separately from the operations array. Adding a new menu operation required editing two different places.

**What We Fixed in Version 2:**

We created a `BookSearchStrategy` interface. Each search type (by name, by author, by publisher) became its own small class. Adding a new search type now only requires adding a new class — `SearchBook` itself never needs to change.

The `IOOperation` interface now includes a `label()` method. The menu is generated automatically from the operations array. Adding a new operation means creating a new class and adding it to the array — the menu updates on its own.

**Classes involved:** `BookSearchStrategy`, `SearchByNameStrategy`, `SearchByAuthorStrategy`, `SearchByPublisherStrategy`, `BookSearchStrategyFactory`, `IOOperation`

---

### L — Liskov Substitution Principle
> *Child classes should safely replace parent classes.*

**The Problem in Version 1:**

The `ViewOrders` operation had an `instanceof Admin` check inside it. This means the operation behaved differently depending on whether the current user was an Admin or a Normal User. Swapping one user type for another broke expected behavior.

Some operation classes were closing `System.in` (the Scanner) while others were not. Replacing one operation with another could break input handling for the rest of the session.

**What We Fixed in Version 2:**

Removed the `instanceof` check from `ViewOrders`. Admin-only actions now live inside Admin's own operations array — Normal Users never even see those options.

Introduced `ConsoleIO` which holds a single shared Scanner. No operation class closes it. Every operation reads input the same safe way. You can now swap any operation for another without side effects.

**Classes involved:** `ConsoleIO`, `ViewOrders`, `Admin`, `NormalUser`

---

### I — Interface Segregation Principle
> *Don't force a class to depend on things it doesn't use.*

**The Problem in Version 1:**

Every single operation class received the full `Database` object, even if it only needed one small part. For example, `Exit` only calls `System.exit(0)` but was still handed the entire database. `ViewBooks` only needed book data but received everything.

**What We Fixed in Version 2:**

We split data access into four focused interfaces:

| Interface | Responsibility |
|---|---|
| `BookRepository` | Only book-related methods |
| `UserRepository` | Only user-related methods |
| `OrderRepository` | Only order-related methods |
| `BorrowingRepository` | Only borrowing-related methods |

Each service and operation now depends only on the interface it actually needs. Nothing is forced to depend on irrelevant methods.

**Classes involved:** `BookRepository`, `UserRepository`, `OrderRepository`, `BorrowingRepository`, `AppContext`

---

### D — Dependency Inversion Principle
> *Depend on interfaces, not on concrete classes.*

**The Problem in Version 1:**

`Main`, `Admin`, `NormalUser`, and every operation class directly used the concrete `Database` class. If the storage format ever changed, every single class would need to be updated.

**What We Fixed in Version 2:**

`BookService` now depends on `BookRepository` (an interface), not `FileBookRepository` (the concrete class). `BorrowingService` depends on `BorrowingRepository`, and so on.

Only `Main.java` knows about the concrete file-based classes. It creates them and passes them upward through the system.

If we ever switch from file storage to a real database, we only change one place — `Main.java`. All business logic stays completely the same.

**Classes involved:** `LibraryService`, `RepositoryFactory`, `FileRepositoryFactory`, `Main`

---

## 🚨 Code Smells

Code smells are signs of bad design. They don't break the program but make it hard to maintain, extend, and understand. Here are the smells we found in Version 1 and how we fixed them.

---

### Smell 1 — Large Class (God Class)

**Where We Found It:** `Database.java` in Version 1.

This class was over 500 lines long and was responsible for too many things at once:
- Storing all users, books, orders, and borrowings in memory
- Reading all 4 types of data from files
- Writing all 4 types of data to files
- Parsing the custom text format for all 4 entities
- Business logic like confirming orders and borrowing books
- Login authentication

This is called a "God Class" because it knows everything and does everything. Any change anywhere in the system meant opening `Database.java` — which was risky and confusing.

**How We Solved It:**

We decomposed `Database.java` into 8 smaller, focused classes. Four repository classes handle file reading and writing, one per entity. Four service classes handle business logic, one per domain area. Now each class has exactly one reason to change, and you can understand any single class in under a minute.

---

### Smell 2 — Long Method

**Where We Found It:** `Database.java`, `SearchBook.java`, `CalculateFine.java`, `Main.java`

Methods in Version 1 were doing too many steps at once — reading user input, validating it, running business logic, and printing output, all inside the same method. Some methods were 50–80 lines long with no clear structure.

For example, the search method in `SearchBook` was handling the menu display, reading the choice, running the search, and printing results — four different jobs in one method.

**How We Solved It:**

We split long methods into smaller focused ones. Input reading was moved to `ConsoleIO`. Business logic was moved to service classes. Display logic stayed in the operation classes. Each method now has one clear job and is short enough to understand at a glance.

---

### Smell 3 — Duplicated Code

**Where We Found It:** `AddBook.java`, `PlaceOrder.java`, `MyOrders.java`, `ViewOrders.java`, `Admin.java`, `NormalUser.java`

The exact same `readInt()` and `readDouble()` input-validation loops were copy-pasted into at least 6 different classes. Every copy had the same structure: print a prompt, read a line, try to parse it, catch the exception, and loop again if invalid.

The problem: if there was a bug in that logic, it had to be fixed in 6 places. If the behavior needed to change (e.g. better error messages), every copy needed updating — and it was easy to miss one.

**How We Solved It:**

We created `ConsoleIO.java` as the single home for all input and output logic. It has `readInt()`, `readIntInRange()`, `readDouble()`, and `readLine()` methods. Every class now calls `ConsoleIO` instead of duplicating the logic. One place to fix, one place to test, one place to change.

---

### Smell 4 — Feature Envy

**Where We Found It:** `CalculateFine.java`

Feature Envy means a method is more interested in the data of another class than in its own. In Version 1, `CalculateFine.oper()` was doing this:

```java
long daysLeft = b.getDaysLeft();
if (daysLeft < 0) {
    long overdueDays = -daysLeft;
    long fine = overdueDays * 10;
}
```

`CalculateFine` was pulling data out of `Borrowing` and doing `Borrowing`'s own math externally. The calculation logic was in the wrong class entirely. `Borrowing` is the one that knows its own dates — it should be the one calculating its own fine.

**How We Solved It:**

We added three methods directly into `Borrowing.java`:
- `isOverdue()` — returns true if the book is overdue
- `daysOverdue()` — returns how many days overdue it is
- `calculateFine(rate)` — returns the total fine amount

Now `CalculateFine` simply calls:

```java
if (borrowing.isOverdue()) {
    System.out.println(borrowing.calculateFine(FINE_PER_DAY));
}
```

The data and the behavior that works on that data now live in the same class — which is where they belong.

---

### Smell 5 — Switch / If-Else Smell

**Where We Found It:** `SearchBook.java`, `UserFactory.java`, `Main.java`

In Version 1, `SearchBook` had this kind of logic:

```java
if (option == 1) field = b.getName();
else if (option == 2) field = b.getAuthor();
else field = b.getPublisher();
```

And `UserFactory` used similar `if/else` chains to decide which type of `User` object to create. Every time a new search type or user type was added, these chains had to grow — which means editing existing, already-working code. This is fragile and error-prone.

**How We Solved It:**

For search — we replaced the `if/else` chain with the Strategy Pattern. Each option became its own class. `BookSearchStrategyFactory` picks the right one. `SearchBook` never needs to change when a new type is added.

For user creation — `UserType` enum now acts as a factory. Each enum value (`ADMIN`, `NORMAL_USER`) has its own `createUser()` method. Adding a new user type means adding a new enum value — no `if/else` chains anywhere.

---

### Smell 6 — Primitive Obsession

**Where We Found It:** `User.java`, `Database.java`, `Order.java`, `Borrowing.java`

In Version 1, user type was just a raw `String`. The code compared it like this in multiple places:

```java
if (type.equalsIgnoreCase("Admin")) { ... }
if (type.equalsIgnoreCase("Normal User")) { ... }
```

A typo in any one of those string comparisons would cause a silent bug that is very hard to trace. Similarly, login credentials were passed as 3 separate loose `String` parameters with no structure and no meaning.

**How We Solved It:**

`UserType` enum replaces the raw `String`. It is type-safe, IDE-autocompleted, and impossible to misspell. The compiler catches mistakes instead of the program silently failing.

We also created proper value objects to group related fields:

| Class | Groups |
|---|---|
| `LoginRequest` | Phone, email, and password |
| `UserRegistration` | All fields needed to register |
| `BookDetails` | All fields needed to add a book |
| `OrderRequest` | All fields needed to place an order |

Instead of passing 4–6 loose parameters, you pass one meaningful object. The code becomes self-documenting.

---

### Smell 7 — Magic Numbers

**Where We Found It:** `CalculateFine.java`, `ReturnBook.java`

The fine rate was hardcoded as the number `10` in two different places:

```java
long fine = overdueDays * 10;  // in CalculateFine
overdueDays * 10               // in ReturnBook
```

The number `10` by itself has no meaning to a reader — you have to guess what it represents. If the fine rate ever changed, two places needed updating, and missing one would cause inconsistent fine calculations.

**How We Solved It:**

We declared a named constant in `CalculateFine`:

```java
private static final long FINE_PER_DAY = 10;
```

And passed it to the `Borrowing` object:

```java
borrowing.calculateFine(FINE_PER_DAY)
```

Now the number has a name. One place to change. The code explains itself.

---

### Smell 8 — Data Class

**Where We Found It:** `Book.java`, `Order.java`, `User.java`

These classes only had fields and getters/setters. They had no real behavior of their own. All logic that worked on their data lived elsewhere — mostly inside `Database.java` or `CalculateFine.java`. This separates data from behavior, which is the opposite of good object-oriented design.

**How We Solved It:**

We moved relevant behavior into the model classes. `Borrowing` now knows how to calculate its own fine. Book-related business logic lives in `BookService`. The objects became active participants in the logic instead of just passive data containers.

---

### Smell 9 — Message Chain

**Where We Found It:** `AddBook.java`, `BorrowBook.java`, `ReturnBook.java`, `PlaceOrder.java`, `SearchBook.java`

Operation classes were making chained calls like:

```java
ctx.getService().getBooks()
ctx.getLibrary().getBorrowingService().findAll()
```

The caller had to know too much about the internal structure of multiple objects just to get what it needed. This creates tight coupling — if the internal structure changes, every chain that goes through it breaks.

**How We Solved It:**

`AppContext` now exposes services directly with clean methods:

```java
ctx.bookService()
ctx.borrowingService()
ctx.orderService()
```

Each operation pulls exactly what it needs in one call. No chains, no deep navigation.

---

### Smell 10 — Dead Code

**Where We Found It:** `Search.java` (in both versions), `Database.java` (left in Version 2 unused)

`Search.java` contains `linearSearch()` and `binarySearch()` methods that work on integer arrays. These methods are never called anywhere in the application. They were likely written as an early experiment but never properly connected to the actual search feature.

`Database.java` from Version 1 was copied into Version 2 and left there — even though the entire new architecture replaced it completely. It sits there unused and confusing.

**How We Solved It (should be done):**

Both files should simply be deleted. Dead code adds confusion without adding any value. A future developer reading the project would not know whether these files are still in use or not.

---

## 🎨 Design Patterns

Design patterns are proven solutions to common software problems. Here are the patterns we applied in Version 2, where we found the problem, and why we chose each pattern.

---

### Pattern 1 — Strategy Pattern

**Classes Involved:**

| Class | Role |
|---|---|
| `BookSearchStrategy.java` | The interface |
| `SearchByNameStrategy.java` | Concrete strategy |
| `SearchByAuthorStrategy.java` | Concrete strategy |
| `SearchByPublisherStrategy.java` | Concrete strategy |
| `BookSearchStrategyFactory.java` | Picks the right strategy |
| `SearchBook.java` | Uses the strategy |

**Where We Found the Problem:**

In Version 1, `SearchBook.java` had a private method called `searchBooks()` that looked like this:

```java
if (option == SearchOption.NAME) {
    return ctx.bookService().searchByName(key);
}
if (option == SearchOption.AUTHOR) {
    return ctx.bookService().searchByAuthor(key);
}
return ctx.bookService().searchByPublisher(key);
```

Every time someone wanted to add a new search type (for example, search by ISBN or search by year), they had to open `SearchBook.java` and edit this method. This violates the Open/Closed Principle — the class was not closed for modification. The `if/else` chain would keep growing with every new feature, making the class harder to understand and riskier to change.

**How We Solved It:**

We created a `BookSearchStrategy` interface with two methods:
- `search(BookService bookService, String key)` — runs the search
- `getSearchType()` — returns the label like `"Book name"` or `"Author"`

Each search type became its own small class:

```java
// SearchByNameStrategy
public List<Book> search(BookService bookService, String key) {
    return bookService.searchByName(key);
}
public String getSearchType() { return "Book name"; }
```

`BookSearchStrategyFactory` picks the right strategy based on the user's numeric choice (1, 2, or 3). `SearchBook.oper()` now looks like this:

```java
BookSearchStrategy strategy = BookSearchStrategyFactory.fromChoice(choice);
List<Book> result = strategy.search(ctx.bookService(), key);
io.readLine("Enter " + strategy.getSearchType() + ": ");
```

`SearchBook` no longer knows anything about which field is being searched. It just delegates to the strategy.

**Why We Chose This Pattern:**

1. We have a family of related algorithms (search by name, author, publisher) that need to be interchangeable.
2. We want to add new search types without touching existing code (OCP).
3. The prompt label (`"Enter Book name: "` vs `"Enter Author: "`) now comes from the strategy itself via `getSearchType()` — so everything about a search type lives in one class.

> Adding "Search by ISBN" would mean: create `SearchByISBNStrategy.java` (10 lines) + add `case 4` to `BookSearchStrategyFactory`. `SearchBook.java` never changes.

---

### Pattern 2 — Abstract Factory Pattern

**Classes Involved:**

| Class | Role |
|---|---|
| `RepositoryFactory.java` | The abstract factory interface |
| `FileRepositoryFactory.java` | The concrete factory |
| `FileBookRepository.java` | Product — file-based |
| `FileUserRepository.java` | Product — file-based |
| `FileOrderRepository.java` | Product — file-based |
| `FileBorrowingRepository.java` | Product — file-based |

**Where We Found the Problem:**

In Version 1, `Database.java` was the only way to access data. Every operation class directly depended on this one concrete class.

In the intermediate version, we had already created the four separate `File*Repository` classes but there was **no factory coordinating them**. Each repository was being created with `new` calls scattered across the codebase.

The problems this caused:
- `new FileBookRepository()` calls spread everywhere
- No guarantee all 4 repositories came from the same "family"
- Switching from file storage to a database would require finding and replacing every single `new File*` call
- The system was tightly coupled to file-based storage

**How We Solved It:**

We created `RepositoryFactory` as an interface:

```java
public interface RepositoryFactory {
    UserRepository      createUserRepository();
    BookRepository      createBookRepository();
    OrderRepository     createOrderRepository();
    BorrowingRepository createBorrowingRepository();
}
```

`FileRepositoryFactory` implements this and creates all 4 file-based repositories:

```java
public BookRepository createBookRepository() {
    return new FileBookRepository();
}
// ... same for the other 3
```

In `Main.java`, only **ONE line** decides the storage system:

```java
RepositoryFactory factory = new FileRepositoryFactory(userFactory);
```

Everything built from there — all services, all operations — only ever sees the repository interfaces, never the concrete file classes.

**Why We Chose This Pattern:**

1. You have a **family** of related objects that must be used together (all 4 repositories must be file-based OR all database-based — never mixed). The factory guarantees this consistency.
2. You want the rest of the system to be completely unaware of which concrete family is in use.
3. You want to swap the entire storage system by changing one line of code.

> Switching to MySQL: write `DatabaseRepositoryFactory` → change ONE line in `Main.java` → every service and operation stays exactly the same.

---

### Pattern 3 — Command Pattern

**Classes Involved:**

| Class | Role |
|---|---|
| `IOOperation.java` | The command interface |
| `AddBook`, `DeleteBook`, `BorrowBook`, `ReturnBook` | Concrete commands |
| `PlaceOrder`, `ViewBooks`, `SearchBook`, `CalculateFine` | Concrete commands |
| `ViewOrders`, `Exit` | Concrete commands |
| `User.java` | The invoker — runs commands |

**Where We Found the Problem:**

In Version 1, `Admin.java` and `NormalUser.java` each had their own `menu()` method that hardcoded menu text **separately** from the operations array:

```java
// In Admin.java
this.operations = new IOOperation[] {
    new ViewBooks(),    // index 0
    new AddBook(),      // index 1
    new DeleteBook(),   // index 2
};
// And SEPARATELY in the menu() method:
System.out.println("1. View Books");
System.out.println("2. Add Book");
System.out.println("3. Delete Book");
```

These two things — the array and the printed text — had to be manually kept in sync. If a developer added a new operation to the array but forgot to add the matching `println`, the menu would display the wrong label for that option.

**How We Solved It:**

We added a `label()` method to the `IOOperation` interface:

```java
public interface IOOperation {
    String label();                       // what to show in the menu
    void oper(AppContext ctx, User user);  // what to do when chosen
}
```

Every command class now implements both:

```java
public class AddBook implements IOOperation {
    public String label() { return "Add Book"; }
    public void oper(AppContext ctx, User user) { ... }
}
```

`User.menu()` now generates the menu automatically:

```java
for (int i = 0; i < operations.length; i++) {
    System.out.println((i+1) + ". " + operations[i].label());
}
int choice = io.readIntInRange("Choose: ", 1, operations.length);
operations[choice - 1].oper(ctx, this);
```

The menu text and the operation logic are now in the **same class**. They can never go out of sync because they are the same object.

**Why We Chose This Pattern:**

1. Each user action is naturally a "command" — a discrete, named action that can be executed when requested.
2. The invoker (`User.menu`) does not need to know what each command does — it just calls `oper()` on whatever the user chose.
3. Adding a new feature is as simple as writing a new class and adding it to the operations array — no other class needs to change.
4. The `label()` method makes this especially powerful — the command describes itself, so the menu is always automatically correct.

---

### Pattern 4 — Repository Pattern

**Classes Involved:**

| Class | Role |
|---|---|
| `BookRepository.java` | Interface |
| `UserRepository.java` | Interface |
| `OrderRepository.java` | Interface |
| `BorrowingRepository.java` | Interface |
| `FileBookRepository.java` | File-based implementation |
| `FileUserRepository.java` | File-based implementation |
| `FileOrderRepository.java` | File-based implementation |
| `FileBorrowingRepository.java` | File-based implementation |
| `BookService.java` | Uses `BookRepository` |
| `BorrowingService.java` | Uses `BorrowingRepository` |

**Where We Found the Problem:**

In Version 1, every operation class that needed to read or write data called `Database` directly:

```java
// Inside BorrowBook.java (Version 1)
Book found = database.findBookByName(bookname);
database.saveBooks();
database.addBorrowing(borrowing);
database.saveBorrowings();
```

Business logic and file I/O were completely mixed together. There was no separation between "what the app does" and "how it stores data." This meant:
- You could not test business logic without reading/writing files
- Changing the file format affected business logic classes
- Every operation class was coupled to the storage mechanism

**How We Solved It:**

We created a repository interface for each entity. Each interface looks like a simple in-memory collection:

```java
public interface BookRepository {
    List<Book> findAll();
    Optional<Book> findByName(String name);
    void save(Book book);
    void deleteByName(String name);
    void saveAll(List<Book> books);
}
```

`BookService` uses this interface and has **zero knowledge of files**:

```java
public List<Book> searchByAuthor(String key) {
    List<Book> result = new ArrayList<>();
    for (Book book : bookRepo.findAll()) {
        if (book.getAuthor().toLowerCase().contains(key))
            result.add(book);
    }
    return result;
}
```

`FileBookRepository` is the **only** class that knows about files. It implements `BookRepository` and does the actual file reading and writing — but nothing outside it ever sees file operations.

**Why We Chose This Pattern:**

The Repository Pattern creates a clean wall between two different concerns:
- **What the application does** — business logic in services
- **How data is stored** — file I/O in repositories

This wall is important because:
1. Business logic can be tested with a simple `InMemoryBookRepository` — no files, no disk, instant tests.
2. The storage format can change without touching any service or operation class.
3. Services are easier to read because they speak in domain language (`findByName`, `save`) not in file language (`Files.readString`, `BufferedWriter`, `split`, `parse`).
4. It is the structural foundation that makes DIP work — services depend on the repository interface, not the file class.

---

## 🗂️ Project Structure (Version 2)

```
src/library/management/system/
│
├── Main.java                    ← entry point, wires everything together
├── AppContext.java              ← holds all services, passed to operations
│
├── ── Models ──
├── Book.java
├── User.java / Admin.java / NormalUser.java
├── Order.java / Borrowing.java
├── UserType.java                ← enum (replaces raw "Admin" strings)
│
├── ── Value Objects ──
├── BookDetails.java
├── LoginRequest.java
├── UserRegistration.java
├── OrderRequest.java
│
├── ── Repository Interfaces ──
├── BookRepository.java
├── UserRepository.java
├── OrderRepository.java
├── BorrowingRepository.java
│
├── ── File Implementations ──
├── FileBookRepository.java
├── FileUserRepository.java
├── FileOrderRepository.java
├── FileBorrowingRepository.java
│
├── ── Abstract Factory ──
├── RepositoryFactory.java       ← interface
├── FileRepositoryFactory.java   ← concrete factory
│
├── ── Services (business logic) ──
├── BookService.java
├── UserService.java
├── OrderService.java
├── BorrowingService.java
├── LibraryService.java
│
├── ── Strategy Pattern ──
├── BookSearchStrategy.java      ← interface
├── SearchByNameStrategy.java
├── SearchByAuthorStrategy.java
├── SearchByPublisherStrategy.java
├── BookSearchStrategyFactory.java
│
├── ── Command Pattern (IOOperation) ──
├── IOOperation.java             ← interface
├── AddBook.java / DeleteBook.java / ViewBooks.java
├── BorrowBook.java / ReturnBook.java / CalculateFine.java
├── PlaceOrder.java / ViewOrders.java / MyOrders.java
├── SearchBook.java / Exit.java
│
└── ── Utilities ──
    ├── ConsoleIO.java           ← all I/O in one place
    ├── DataFiles.java
    └── ParseUtil.java
```

---

## 🔗 Links

| | Link |
|---|---|
| Version 1 (main branch) | https://github.com/yeasinkafi/Library-Management-System-Java-/tree/main |
| Version 2 (updated branch) | https://github.com/yeasinkafi/Library-Management-System-Java-/tree/Updates |
