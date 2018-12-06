regExps = {
"exercise_1": /[A-Z][a-z]+/,
"exercise_2": /088[1-9]{7}/,
"exercise_3": /[^0-1]+/,
"exercise_4": /^[a-zA-Z][a-zA-Z0-9\._]{2,21}$/,
"exercise_5": /^[19]\d+[09]$/,
"exercise_6": /class=['"].*['"]/
};
cssSelectors = {
"exercise_1": "item > java.class1",
"exercise_2": "different > java.diffClass",
"exercise_3": "java.class1 + tag",
"exercise_4": "css > item:nth-child(3)",
"exercise_5": "tag > java:nth-child(2)",
"exercise_6": "item.class1 > item.class2, item.class2 > item.class1",
"exercise_7": "different#diffId2 > java:last-of-type",
"exercise_8": "css > item:nth-child(2)"
};
