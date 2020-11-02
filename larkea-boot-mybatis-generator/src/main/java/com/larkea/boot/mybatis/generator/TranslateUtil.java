package com.larkea.boot.mybatis.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 英文单词单复数转换工具类.
 */
public class TranslateUtil {

	private static final Pattern UNDERSCORE_PATTERN_1 = Pattern.compile("([A-Z]+)([A-Z][a-z])");

	private static final Pattern UNDERSCORE_PATTERN_2 = Pattern.compile("([a-z\\d])([A-Z])");

	private static List<RuleAndReplacement> PLURALS = new ArrayList<>();

	private static List<RuleAndReplacement> SINGULARS = new ArrayList<>();

	private static List<String> UNCOUNTABLES = new ArrayList<>();

	static {
		initialize();
	}

	public static String underscore(String camelCasedWord) {

		String underscoredWord = UNDERSCORE_PATTERN_1.matcher(camelCasedWord).replaceAll("$1_$2");
		underscoredWord = UNDERSCORE_PATTERN_2.matcher(underscoredWord).replaceAll("$1_$2");
		underscoredWord = underscoredWord.replace('-', '_').toLowerCase();

		return underscoredWord;
	}

	public static String pluralize(String word) {
		if (UNCOUNTABLES.contains(word.toLowerCase())) {
			return word;
		}
		return replaceWithFirstRule(word, PLURALS);
	}

	public static String singularize(String word) {
		if (UNCOUNTABLES.contains(word.toLowerCase())) {
			return word;
		}
		return replaceWithFirstRule(word, SINGULARS);
	}

	private static String replaceWithFirstRule(String word, List<RuleAndReplacement> ruleAndReplacements) {

		for (RuleAndReplacement rar : ruleAndReplacements) {
			String rule = rar.getRule();
			String replacement = rar.getReplacement();

			// Return if we find a match.
			Matcher matcher = Pattern.compile(rule, Pattern.CASE_INSENSITIVE).matcher(word);
			if (matcher.find()) {
				return matcher.replaceAll(replacement);
			}
		}
		return word;
	}

	public static String tableize(String className) {
		return pluralize(underscore(className));
	}

	public static void plural(String rule, String replacement) {
		PLURALS.add(0, new RuleAndReplacement(rule, replacement));
	}

	public static void singular(String rule, String replacement) {
		SINGULARS.add(0, new RuleAndReplacement(rule, replacement));
	}

	public static void irregular(String singular, String plural) {
		plural(singular, plural);
		singular(plural, singular);
	}

	public static void uncountable(String... words) {
		Collections.addAll(UNCOUNTABLES, words);
	}

	private static void initialize() {
		plural("$", "s");
		plural("s$", "s");
		plural("(ax|test)is$", "$1es");
		plural("(octop|vir)us$", "$1i");
		plural("(alias|status)$", "$1es");
		plural("(bu)s$", "$1es");
		plural("(buffal|tomat)o$", "$1oes");
		plural("([ti])um$", "$1a");
		plural("sis$", "ses");
		plural("(?:([^f])fe|([lr])f)$", "$1$2ves");
		plural("(hive)$", "$1s");
		plural("([^aeiouy]|qu)y$", "$1ies");
		plural("([^aeiouy]|qu)ies$", "$1y");
		plural("(x|ch|ss|sh)$", "$1es");
		plural("(matr|vert|ind)ix|ex$", "$1ices");
		plural("([m|l])ouse$", "$1ice");
		plural("(ox)$", "$1es");
		plural("(quiz)$", "$1zes");

		singular("s$", "");
		singular("(n)ews$", "$1ews");
		singular("([ti])a$", "$1um");
		singular("((a)naly|(b)a|(d)iagno|(p)arenthe|(p)rogno|(s)ynop|(t)he)ses$", "$1$2sis");
		singular("(^analy)ses$", "$1sis");
		singular("([^f])ves$", "$1fe");
		singular("(hive)s$", "$1");
		singular("(tive)s$", "$1");
		singular("([lr])ves$", "$1f");
		singular("([^aeiouy]|qu)ies$", "$1y");
		singular("(s)eries$", "$1eries");
		singular("(m)ovies$", "$1ovie");
		singular("(x|ch|ss|sh)es$", "$1");
		singular("([m|l])ice$", "$1ouse");
		singular("(bus)es$", "$1");
		singular("(o)es$", "$1");
		singular("(shoe)s$", "$1");
		singular("(cris|ax|test)es$", "$1is");
		singular("([octop|vir])i$", "$1us");
		singular("(alias|status)es$", "$1");
		singular("^(ox)es", "$1");
		singular("(vert|ind)ices$", "$1ex");
		singular("(matr)ices$", "$1ix");
		singular("(quiz)zes$", "$1");

		irregular("person", "people");
		irregular("man", "men");
		irregular("child", "children");
		irregular("sex", "sexes");
		irregular("move", "moves");

		uncountable("equipment", "information", "rice", "money", "species", "series", "fish", "sheep");
	}

	public static String tableize(Class<?> klass) {
		String className = klass.getName().replace(klass.getPackage().getName() + ".", "");
		return tableize(className);
	}
}

class RuleAndReplacement {

	private String rule;

	private String replacement;

	public RuleAndReplacement(String rule, String replacement) {
		this.rule = rule;
		this.replacement = replacement;
	}

	public String getReplacement() {
		return replacement;
	}

	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}
}
