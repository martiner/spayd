# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

`geek-spayd` is a small Java 17 library that builds SPAYD (Short Payment Descriptor, "QR Platba", http://qr-platba.cz/) strings and renders them as QR code images via ZXing. It is published to Maven Central as `cz.geek:geek-spayd`; most build configuration (plugins, release, signing) is inherited from the `cz.geek:geek-parent` POM, not defined here.

## Commands

Use the Maven wrapper; the JDK version comes from `.java-version`.

- Build and test (what CI runs): `./mvnw -B verify javadoc:javadoc`
- Run tests: `./mvnw test`
- Single test class: `./mvnw test -Dtest=CzechBankAccountTest`
- Single test method: `./mvnw test -Dtest=CzechBankAccountTest#methodName`

Javadoc generation is part of CI, so broken Javadoc fails the build.

`src/test/java/cz/geek/spayd/Payment.java` is not a test but a `main` that regenerates `payment.png` (the image shown in the README) in the working directory.

Releases are cut with `maven-release-plugin` (`[maven-release-plugin] prepare ...` commits).

## Architecture

Everything lives in package `cz.geek.spayd`.

- `SpaydPayment` holds an ordered `LinkedHashMap<String, SpaydValue>` keyed by SPAYD field codes (`ACC`, `AM`, `CC`, `MSG`, ...). `asString()` serializes it as `SPD*1.0*KEY:value*...`, in insertion order. Typed setters/getters are thin wrappers over `putSimpleValue` / `getValue(key, Class)`; getters unwrap `SpaydWrappedValue` and throw `IllegalStateException` on a type mismatch.
- `CzechSpaydPayment` extends it with the Czech `X-*` fields (VS, KS, SS, PER, ID, URL) and defaults currency to CZK. New fields follow the same pattern: a key constant plus a setter/getter pair.
- `SpaydValue` is the serialization contract (`asString()`). Implementations:
  - `SimpleValue`: any value via `toString()`, trimmed and escaped (non-ASCII URL-encoded; `*`, `+`, `%` percent-encoded, since `*` is the SPAYD field separator).
  - `SpaydDate`: `yyyyMMdd`.
  - `BankAccount`: `IBAN` or `IBAN+BIC`. `CzechBankAccount` converts a Czech `prefix-number/bank` account into an IBAN, validating prefix and number with the mod-11 check and computing the IBAN check digits.
  - `AlternateAccounts`: comma-joined list of `BankAccount`s.
- `SpaydQRFactory` encodes the payment string (ISO-8859-1, error correction M) and, when `branded` (default), draws a border and the "QR Platba" label under the code. Size is bounded by `MIN_QR_SIZE`..`MAX_QR_SIZE`. I/O failures are wrapped in the unchecked `SpaydQRException`.

Argument validation uses `org.apache.commons.lang3.Validate`. Tests use JUnit 4 with Hamcrest.
