# Geek SPAYD

[![Build Status](https://github.com/martiner/spayd/actions/workflows/build-branch.yml/badge.svg?branch=master)](https://github.com/martiner/spayd/actions/workflows/build-branch.yml)
[![Maven Central](https://maven-badges.sml.io/maven-central/cz.geek/geek-spayd/badge.svg)](https://maven-badges.sml.io/maven-central/cz.geek/geek-spayd)
[![Javadocs](http://javadoc.io/badge/cz.geek/geek-spayd.svg)](http://javadoc.io/doc/cz.geek/geek-spayd)

A Java library for generating [SPAYD (Short Payment Descriptor)](http://qr-platba.cz/) QR codes
which doesn't [suck](https://github.com/spayd/spayd-java).

## Usage

```xml
<dependency>
	<groupId>cz.geek</groupId>
	<artifactId>geek-spayd</artifactId>
	<version>${spayd.version}</version>
</dependency>
```

```java
final BankAccount account = new CzechBankAccount("2000009442/2010");
final CzechSpaydPayment payment = new CzechSpaydPayment(account);
payment.setAmount(100);
payment.setDate(new Date());
payment.setVs("123");
payment.setRecipientName("martiner");
payment.setMessage("Thanks for making Geek SPAYD!");

final SpaydQRFactory factory = new SpaydQRFactory();
factory.saveQRCode(payment, "png", new File("payment.png"));
```
![QR Platba](payment.png)

## TODO

- validations