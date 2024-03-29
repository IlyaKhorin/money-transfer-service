# money-transfer-service
RESTful API for money transfers between accounts

## Task
Design and implement a RESTful API (including data model and the backing implementation) for
money transfers between accounts.

**Explicit requirements:**
1. You can use Java or Kotlin.
2. Keep it simple and to the point (e.g. no need to implement any authentication).
3. Assume the API is invoked by multiple systems and services on behalf of end users.
4. You can use frameworks/libraries if you like (except Spring), but don't forget about
requirement #2 and keep it simple and avoid heavy frameworks.
5. The datastore should run in-memory for the sake of this test.
6. The final result should be executable as a standalone program (should not require a
pre-installed container/server).
7. Demonstrate with tests that the API works as expected.

**Implicit requirements:**
1. The code produced by you is expected to be of high quality.
2. There are no detailed requirements, use common sense.

## Simplifications
1. No Auth
2. InMemory Db implemented by HashMap
3. No transaction rollback, persistance
4. **My primary skill is C# so something in code may look wierd**
