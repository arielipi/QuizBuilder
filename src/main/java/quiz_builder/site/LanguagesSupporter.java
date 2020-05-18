package quiz_builder.site;
 
import java.io.Serializable;
 
public class LanguagesSupporter implements Serializable {
       
        public static String getGamesListTitle(String languaget) {
                if(languaget.equals("English")) {
                        return "Quiz Builder";
                } else if(languaget.equals("Hebrew")) {
                        return "בונה משחקים";
                }
                return "Quiz Builder";
        }
       
        public static String downloadTemplateAlertFillFile(String languaget) {
            if(languaget.equals("English")) {
                    return "Please choose a file according to the format";
            } else if(languaget.equals("Hebrew")) {
                    return "בחר קובץ";
            }
            return "Please choose a file according to the format";
        }
        public static String gamesListStats(String languaget) {
            if(languaget.equals("English")) {
                    return "Stats";
            } else if(languaget.equals("Hebrew")) {
                    return "נתונים";
            }
            return "Stats";
        }
       
        public static String welcomeToQuizBuilder(String languaget) {
                if(languaget.equals("English")) {
                        return "Welcome to Quiz Builder!";
                } else if(languaget.equals("Hebrew")) {
                        return "!ברוך הבא לבונה משחקים";
                }
                return "Welcome to Quiz Builder!";
        }
       
        public static String gamesListInfo(String languaget) {
                if(languaget.equals("English")) {
                        return "Choose a game to edit, or create a new game.";
                } else if(languaget.equals("Hebrew")) {
                        return ".ערוך משחק  או צור משחק";
                }
                return "Choose a game to edit, or create a new game.";
        }
       
        public static String gamesListYourGames(String languaget) {
                if(languaget.equals("English")) {
                        return "Your games:";
                } else if(languaget.equals("Hebrew")) {
                        return ":משחקיך";
                }
                return "Your games:";
        }
       
        public static String gamesListYouHaveNoGames(String languaget) {
                if(languaget.equals("English")) {
                        return "You have no games in the database.";
                } else if(languaget.equals("Hebrew")) {
                        return ".אין לך משחקים במסד הנתונים";
                }
                return "You have no games in the database.";
        }
       
        public static String gamesListYouMustBeLogged(String languaget) {
                if(languaget.equals("English")) {
                        return "You must be logged in to view games";
                } else if(languaget.equals("Hebrew")) {
                        return "היכנס למשתמש על מנת לצפות במשחקים";
                }
                return "You must be logged in to view games";
        }
       
        public static String gamesListPublicGames(String languaget) {
                if(languaget.equals("English")) {
                        return "Public games:";
                } else if(languaget.equals("Hebrew")) {
                        return ":משחקים ציבוריים";
                }
                return "Public games:";
        }
       
        public static String gamesListNoPublicGames(String languaget) {
                if(languaget.equals("English")) {
                        return "There are no public games.";
                } else if(languaget.equals("Hebrew")) {
                        return ".אין משחקים ציבוריים";
                }
                return "There are no public games.";
        }
       
        public static String gamesListCreateNewGame(String languaget) {
                if(languaget.equals("English")) {
                        return "Create a new game:";
                } else if(languaget.equals("Hebrew")) {
                        return ":צור משחק חדש";
                }
                return "Create a new game:";
        }
       
        public static String gamesListCreateNewPublicGame(String languaget) {
                if(languaget.equals("English")) {
                        return "Create a new public game:";
                } else if(languaget.equals("Hebrew")) {
                        return ":צור משחק ציבורי חדש";
                }
                return "Create a new public game:";
        }
       
       
        public static String gameStatsStatsFor(String languaget) {
                if(languaget.equals("English")) {
                        return "Stats for ";
                } else if(languaget.equals("Hebrew")) {
                        return "נתונים עבור ";
                }
                return "Stats for ";
        }
       
        public static String gameStatsGameStarts(String languaget) {
                if(languaget.equals("English")) {
                        return "Game starts:";
                } else if(languaget.equals("Hebrew")) {
                        return ":התחלות משחק";
                }
                return "Game starts:";
        }
       
        public static String gameStatsGameCompletions(String languaget) {
                if(languaget.equals("English")) {
                        return "Game completions:";
                } else if(languaget.equals("Hebrew")) {
                        return ":סיומי משחק";
                }
                return "Game completions:";
        }
       
        public static String gameStatsAverageScore(String languaget) {
                if(languaget.equals("English")) {
                        return "Average game score:";
                } else if(languaget.equals("Hebrew")) {
                        return ":ניקוד ממוצע";
                }
                return "Average game score:";
        }
       
        public static String gameStatsEditGame(String languaget) {
                if(languaget.equals("English")) {
                        return "Edit game";
                } else if(languaget.equals("Hebrew")) {
                        return "ערוך משחק";
                }
                return "Edit game";
        }
       
        public static String gameStatsBackToMainPage(String languaget) {
                if(languaget.equals("English")) {
                        return "Back to games list";
                } else if(languaget.equals("Hebrew")) {
                        return "לרשימת המשחקים";
                }
                return "Back to games list";
        }
       
        public static String gamePageGame(String languaget) {
                if(languaget.equals("English")) {
                        return "Game";
                } else if(languaget.equals("Hebrew")) {
                        return "משחק";
                }
                return "Game";
        }
       
        public static String gamePageOpen(String languaget) {
                if(languaget.equals("English")) {
                        return "Open";
                } else if(languaget.equals("Hebrew")) {
                        return "פתוחה";
                }
                return "Open";
        }
       
        public static String gamePageAmerican(String languaget) {
                if(languaget.equals("English")) {
                        return "American";
                } else if(languaget.equals("Hebrew")) {
                        return "אמריקאית";
                }
                return "American";
        }
       
        public static String gamePageAmericanImage(String languaget) {
                if(languaget.equals("English")) {
                        return "AmericanImage";
                } else if(languaget.equals("Hebrew")) {
                        return "אמריקאית עם תמונות";
                }
                return "AmericanImage";
        }
       
        public static String gamePageAddLevel(String languaget) {
                if(languaget.equals("English")) {
                        return "Add Level";
                } else if(languaget.equals("Hebrew")) {
                        return "הוסף שלב";
                }
                return "Add Level";
        }
       
        public static String gamePageSave(String languaget) {
                if(languaget.equals("English")) {
                        return "Save Game";
                } else if(languaget.equals("Hebrew")) {
                        return "שמור משחק";
                }
                return "Save Game";
        }
       
        public static String gamePageViewStats(String languaget) {
                if(languaget.equals("English")) {
                        return "View Game Stats";
                } else if(languaget.equals("Hebrew")) {
                        return "נתוני משחק";
                }
                return "View Game Stats";
        }
       
        public static String feedbackFillName(String language) {
                if(language.equals("English")) {
                        return "Please fill name";
                } else if(language.equals("Hebrew")) {
                        return "נא מלא את שמך";
                }
                return "Please fill name";
        }
       
        public static String feedbackName(String language) {
                if(language.equals("English")) {
                        return "Name";
                } else if(language.equals("Hebrew")) {
                        return "שם";
                }
                return "Name";
        }
       
        public static String feedbackDesc(String language) {
                if(language.equals("English")) {
                        return "Description";
                } else if(language.equals("Hebrew")) {
                        return "תיאור";
                }
                return "Description";
        }
       
        public static String feedbackFillFeedback(String language) {
                if(language.equals("English")) {
                        return "Please fill feedback";
                } else if(language.equals("Hebrew")) {
                        return "נא מלא משוב";
                }
                return "Please fill feedback";
        }
       
        public static String feedback(String language) {
                if(language.equals("English")) {
                        return "Feedback";
                } else if(language.equals("Hebrew")) {
                        return "משוב";
                }
                return "Feedback";
        }
       
        public static String feedbackSend(String language) {
                if(language.equals("English")) {
                        return "Send Feedback";
                } else if(language.equals("Hebrew")) {
                        return "שלח משוב";
                }
                return "Send Feedback";
        }
       
        public static String about(String language) {
                if(language.equals("English")) {
                        return "About";
                } else if(language.equals("Hebrew")) {
                        return "אודות";
                }
                return "About";
        }
       
        public static String getAbout(String language) {
                if(language.equals("English")) {
                        return "This project was done as part of the Open University Project course.";
                } else if(language.equals("Hebrew")) {
                        return "פרויקט זה נעשה כחלק מסדנת הפרויקטים של האוניברסיטה הפתוחה.";
                }
                return "This project was done as part of the Open University Project course.";
        }
       
        public static String addQuestion(String language) {
                if(language.equals("English")) {
                        return "Add Question";
                } else if(language.equals("Hebrew")) {
                        return "הוסף שאלה";
                }
                return "Add Question";
        }
       
        public static String addQuestionFillQText(String language) {
                if(language.equals("English")) {
                        return "Please fill question text";
                } else if(language.equals("Hebrew")) {
                        return "מלא את השאלה";
                }
                return "Please fill question text";
        }
       
        public static String addQuestionFillCorrectAnswer(String language) {
                if(language.equals("English")) {
                        return "Please fill correct answer";
                } else if(language.equals("Hebrew")) {
                        return "מלא את התשובה הנכונה";
                }
                return "Please fill correct answer";
        }
       
        public static String addQuestionFillIncorrectAnswer(String language) {
                if(language.equals("English")) {
                        return "Please fill incorrect answer";
                } else if(language.equals("Hebrew")) {
                        return "מלא את התשובה הלא נכונה";
                }
                return "Please fill incorrect answer";
        }
       
        public static String addQuestionFillPoints(String language) {
                if(language.equals("English")) {
                        return "Please fill points";
                } else if(language.equals("Hebrew")) {
                        return "מלא את הנקודות";
                }
                return "Please fill points";
        }
       
        public static String addQuestionFillTime(String language) {
                if(language.equals("English")) {
                        return "Please fill time";
                } else if(language.equals("Hebrew")) {
                        return "מלא את הזמן";
                }
                return "Please fill time";
        }
       
        public static String addQuestionQText(String language) {
                if(language.equals("English")) {
                        return "Question Text";
                } else if(language.equals("Hebrew")) {
                        return "טקסט שאלה";
                }
                return "Question Text";
        }
       
        public static String addQuestionQImage(String language) {
                if(language.equals("English")) {
                        return "Question Image";
                } else if(language.equals("Hebrew")) {
                        return "תמונת שאלה";
                }
                return "Question Image";
        }
       
        public static String addQuestionCorrectAnswer(String language) {
                if(language.equals("English")) {
                        return "Correct Answer";
                } else if(language.equals("Hebrew")) {
                        return "תשובה נכונה";
                }
                return "Correct Answer";
        }
       
        public static String addQuestionIncorrectAnswer(String language) {
                if(language.equals("English")) {
                        return "Incorrect Answers";
                } else if(language.equals("Hebrew")) {
                        return "תשובות שגויות";
                }
                return "Incorrect Answers";
        }
       
        public static String addQuestionPoints(String language) {
                if(language.equals("English")) {
                        return "Points";
                } else if(language.equals("Hebrew")) {
                        return "נקודות";
                }
                return "Points";
        }
       
        public static String addQuestionTime(String language) {
                if(language.equals("English")) {
                        return "Time";
                } else if(language.equals("Hebrew")) {
                        return "זמן";
                }
                return "Time";
        }
       
        public static String addQuestionBackToGamePage(String language) {
                if(language.equals("English")) {
                        return "Back to game page";
                } else if(language.equals("Hebrew")) {
                        return "לדף המשחק";
                }
                return "Back to game page";
        }
       
        public static String generalSubmit(String language) {
                if(language.equals("English")) {
                        return "Submit";
                } else if(language.equals("Hebrew")) {
                        return "שלח";
                }
                return "Submit";
        }
       
        public static String editQuestion(String language) {
                if(language.equals("English")) {
                        return "Edit Question";
                } else if(language.equals("Hebrew")) {
                        return "ערוך שאלה";
                }
                return "Edit Question";
        }
       
        public static String editQuestionToRemoveImage(String language) {
                if(language.equals("English")) {
                        return "To remove this image check the box";
                } else if(language.equals("Hebrew")) {
                        return "להסרת התמונה סמן את התיבה";
                }
                return "To remove this image check the box";
        }
       
        public static String gamesListGuide(String language) {
                if(language.equals("English")) {
                        return "How to use?";
                } else if(language.equals("Hebrew")) {
                        return "איך להשתמש?";
                }
                return "How to use?";
        }
       
        public static String gamesListStepOne(String language) {
                if(language.equals("English")) {
                        return "Edit public games, view stats or delete a game.";
                } else if(language.equals("Hebrew")) {
                        return "ערוך משחק, ראה נתונים או מחק.";
                }
                return "Edit public games, view stats or delete a game.";
        }
       
        public static String gamesListStepTwo(String language) {
                if(language.equals("English")) {
                        return "Create public games.";
                } else if(language.equals("Hebrew")) {
                        return "צור משחק ציבורי.";
                }
                return "Create public games.";
        }
       
        public static String gamesListStepThree(String language) {
                if(language.equals("English")) {
                        return "Edit private games, view stats or delete a game.";
                } else if(language.equals("Hebrew")) {
                        return "ערוך משחק, ראה נתונים או מחק.";
                }
                return "Edit private games, view stats or delete a game.";
        }
       
        public static String gamesListStepFour(String language) {
                if(language.equals("English")) {
                        return "Create private games.";
                } else if(language.equals("Hebrew")) {
                        return "צור משחק פרטי.";
                }
                return "Create private games.";
        }
       
        public static String gamePageStepOne(String language) {
                if(language.equals("English")) {
                        return "Game name.";
                } else if(language.equals("Hebrew")) {
                        return "שם משחק.";
                }
                return "Game name.";
        }
       
        public static String gamePageStepTwo(String language) {
                if(language.equals("English")) {
                        return "Save game (otherwise all changes are undone).";
                } else if(language.equals("Hebrew")) {
                        return "שמור משחק אחרת כל השינויים מבוטלים.";
                }
                return "Save game (otherwise all changes are undone).";
        }
       
        public static String gamePageStepThree(String language) {
                if(language.equals("English")) {
                        return "Stats for this game.";
                } else if(language.equals("Hebrew")) {
                        return "נתונים למשחק זה.";
                }
                return "Stats for this game.";
        }
       
        public static String gamePageStepFour(String language) {
                if(language.equals("English")) {
                        return "Go back to main page (remember to save before you leave).";
                } else if(language.equals("Hebrew")) {
                        return "חזור לדף הראשי, זכור לשמור את המשחק לפני.";
                }
                return "Go back to main page (remember to save before you leave).";
        }
       
        public static String gamePageStepFive(String language) {
                if(language.equals("English")) {
                        return "Create a new level.";
                } else if(language.equals("Hebrew")) {
                        return "צור שלב חדש.";
                }
                return "Create a new level.";
        }
       
        public static String gamePageStepSix(String language) {
                if(language.equals("English")) {
                        return "Edit, create or delete questions and levels.";
                } else if(language.equals("Hebrew")) {
                        return "ערוך, צור או מחק שאלות ושלבים.";
                }
                return "Edit, create or delete questions and levels.";
        }
       
        public static String gamePageStepSeven(String language) {
            if(language.equals("English")) {
                    return "Download this game.";
            } else if(language.equals("Hebrew")) {
                    return "הורד משחק זה.";
            }
            return "Download this game.";
        }
       
        public static String gamePageDownload(String language) {
            if(language.equals("English")) {
                    return "Download";
            } else if(language.equals("Hebrew")) {
                    return "הורדה";
            }
            return "Download";
        }
       
        public static String feedbackStepOne(String language) {
                if(language.equals("English")) {
                        return "Type in your name so we know who you are.";
                } else if(language.equals("Hebrew")) {
                        return "הכנס את שמך כדי שנדע מי אתה.";
                }
                return "Type in your name so we know who you are.";
        }
       
        public static String feedbackStepTwo(String language) {
                if(language.equals("English")) {
                        return "Fill in your feedback, please be informative.";
                } else if(language.equals("Hebrew")) {
                        return "מלא משוב עם מידע מועיל.";
                }
                return "Fill in your feedback, please be informative.";
        }
       
        public static String feedbackStepThree(String language) {
                if(language.equals("English")) {
                        return "Go back to main page.";
                } else if(language.equals("Hebrew")) {
                        return "חזור לדף הראשי.";
                }
                return "Go back to main page.";
        }
       
        public static String addAIQStepOne(String language) {
                if(language.equals("English")) {
                        return "Fill in a question.";
                } else if(language.equals("Hebrew")) {
                        return "הכנס שאלה.";
                }
                return "Fill in a question.";
        }
       
        public static String addAIQStepTwo(String language) {
                if(language.equals("English")) {
                        return "Check to give an image that will accompany the question.";
                } else if(language.equals("Hebrew")) {
                        return "סמן כדי להכניס תמונה שמתלווה לשאלה";
                }
                return "Check to give an image that will accompany the question.";
        }
       
        public static String addAIQStepThree(String language) {
                if(language.equals("English")) {
                        return "Give the correct answer here.";
                } else if(language.equals("Hebrew")) {
                        return "הכנס את התשובה הנכונה כאן";
                }
                return "Give the correct answer here.";
        }
       
        public static String addAIQStepFour(String language) {
                if(language.equals("English")) {
                        return "Give an incorrect answer here.";
                } else if(language.equals("Hebrew")) {
                        return "הכנס תשובה לא נכונה";
                }
                return "Give an incorrect answer here.";
        }
       
        public static String addAIQStepFive(String language) {
                if(language.equals("English")) {
                        return "Click to add another incorrect answer.";
                } else if(language.equals("Hebrew")) {
                        return "לחץ בשביל להוסיף עוד תשובה לא נכונה.";
                }
                return "Click to add another incorrect answer.";
        }
       
        public static String addAIQStepSix(String language) {
                if(language.equals("English")) {
                        return "Click to remove another incorrect answer.";
                } else if(language.equals("Hebrew")) {
                        return "לחץ בשביל להסיר תשובה לא נכונה.";
                }
                return "Click to remove another incorrect answer.";
        }
       
        public static String addAIQStepSeven(String language) {
                if(language.equals("English")) {
                        return "Decide how many points this question is worth.";
                } else if(language.equals("Hebrew")) {
                        return "החלט כמה נקודות שווה השאלה.";
                }
                return "Decide how many points this question is worth.";
        }
       
        public static String addAIQStepEight(String language) {
                if(language.equals("English")) {
                        return "(Optional) Limit time to answer question.";
                } else if(language.equals("Hebrew")) {
                        return "אפשרות להגבלת זמן.";
                }
                return "(Optional) Limit time to answer question.";
        }
       
        public static String editAIQStepThree(String language) {
                if(language.equals("English")) {
                        return "Replace the correct answer here.";
                } else if(language.equals("Hebrew")) {
                        return "החלף את התשובה הנכונה כאן";
                }
                return "Replace the correct answer here.";
        }
       
        public static String editAIQStepFour(String language) {
                if(language.equals("English")) {
                        return "Replace an incorrect answer here.";
                } else if(language.equals("Hebrew")) {
                        return "החלף תשובה לא נכונה כאן";
                }
                return "Replace an incorrect answer here.";
        }
       
        public static String downloadTemplateGameName(String language) {
            if(language.equals("English")) {
                    return "Game Name:";
            } else if(language.equals("Hebrew")) {
                    return "שם משחק";
            }
            return "Game Name:";
        }
        public static String downloadTemplateStepOne(String language) {
            if(language.equals("English")) {
                    return "Set game's name";
            } else if(language.equals("Hebrew")) {
                    return "בחר שם משחק";
            }
            return "Set game's name";
        }
        public static String downloadTemplateLevelName(String language) {
            if(language.equals("English")) {
                    return "Level Name:";
            } else if(language.equals("Hebrew")) {
                    return "שם שלב";
            }
            return "Level Name:";
        }
        public static String downloadTemplateStepTwo(String language) {
            if(language.equals("English")) {
                    return "Set level's name";
            } else if(language.equals("Hebrew")) {
                    return "בחר שם שלב";
            }
            return "Set level's name";
        }
        public static String downloadTemplateStepThree(String language) {
            if(language.equals("English")) {
                    return "Add another level";
            } else if(language.equals("Hebrew")) {
                    return "הוסף שלב";
            }
            return "Add another level";
        }
        public static String downloadTemplateStepFour(String language) {
            if(language.equals("English")) {
                    return "Remove last level";
            } else if(language.equals("Hebrew")) {
                    return "הסר שלב אחרון";
            }
            return "Remove last level";
        }
       
        public static String gamePageStepEight(String language) {
            if(language.equals("English")) {
                    return "Get a template of a game";
            } else if(language.equals("Hebrew")) {
                    return "הורד תבנית למשחק";
            }
            return "Get a template of a game";
        }
        public static String gamePageDownloadTemplate(String language) {
            if(language.equals("English")) {
                    return "Get template";
            } else if(language.equals("Hebrew")) {
                    return "הורד תבנית";
            }
            return "Get template";
        }
        public static String downloadTemplateTitle(String language) {
            if(language.equals("English")) {
                    return "Get a template";
            } else if(language.equals("Hebrew")) {
                    return "צור תבנית";
            }
            return "Get a template";
        }
        public static String downloadTemplateHeader(String language) {
            if(language.equals("English")) {
                    return "Get template";
            } else if(language.equals("Hebrew")) {
                    return "הורד תבנית";
            }
            return "Get template";
        }
        public static String downloadTemplateOpenQuestions(String language) {
            if(language.equals("English")) {
                    return "Number of open questions:";
            } else if(language.equals("Hebrew")) {
                    return "מספר שאלות פתוחות";
            }
            return "Number of open questions:";
        }
        public static String downloadTemplateAmericanQuestions(String language) {
            if(language.equals("English")) {
                    return "Number of american questions:";
            } else if(language.equals("Hebrew")) {
                    return "מספר שאלות אמריקאיות";
            }
            return "Number of american questions:";
        }
        public static String downloadTemplateStepFive(String language) {
            if(language.equals("English")) {
                    return "Number of open questions you want";
            } else if(language.equals("Hebrew")) {
                    return "מספר שאלות פתוחות שאתה רוצה";
            }
            return "Number of open questions you want";
        }
        public static String downloadTemplateStepSix(String language) {
            if(language.equals("English")) {
                    return "Number of american questions you want";
            } else if(language.equals("Hebrew")) {
                    return "מספר שאלות אמריקאיות שאתה רוצה";
            }
            return "Number of american questions you want";
        }
        public static String downloadTemplateAlertFillGameName(String language) {
            if(language.equals("English")) {
                    return "Please fill game name.";
            } else if(language.equals("Hebrew")) {
                    return "נא מלא שם משחק";
            }
            return "Please fill game name.";
        }
        public static String downloadTemplateAlertFillLevelName(String language) {
            if(language.equals("English")) {
                    return "Please fill level name.";
            } else if(language.equals("Hebrew")) {
                    return "נא מלא שם שלב";
            }
            return "Please fill level name.";
        }
        public static String downloadTemplateAlertFillNumberOfOpenQuestions(String language) {
            if(language.equals("English")) {
                    return "Please fill number of open questions.";
            } else if(language.equals("Hebrew")) {
                    return "נא מלא מספר שאלות פתוחות מבוקש";
            }
            return "Please fill number of open questions.";
        }
        public static String downloadTemplateAlertFillNumberOfAmericanQuestions(String language) {
            if(language.equals("English")) {
                    return "Please fill number of american questions.";
            } else if(language.equals("Hebrew")) {
                    return "נא מלא מספר שאלות אמריקאיות מבוקש";
            }
            return "Please fill number of american questions.";
        }
        public static String play(String language) {
            if(language.equals("English")) {
                    return "Play!";
            } else if(language.equals("Hebrew")) {
                    return "שחק!";
            }
            return "Please fill number of american questions.";
        }
        public static String logout(String language) {
            if(language.equals("English")) {
                    return "Logout";
            } else if(language.equals("Hebrew")) {
                    return "התנתק";
            }
            return "Please fill number of american questions.";
        }
        public static String signIn(String language) {
            if(language.equals("English")) {
                    return "Sign in";
            } else if(language.equals("Hebrew")) {
                    return "התחבר";
            }
            return "Please fill number of american questions.";
        }
}