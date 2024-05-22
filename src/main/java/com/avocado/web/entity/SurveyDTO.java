package com.avocado.web.entity;

import java.util.List;

import lombok.Data;

@Data
public class SurveyDTO {
    private String event_id;
    private String event_type;
    private FormResponse form_response;
    
    @Data
    public static class FormResponse {
        private String form_id;
        private String token;
        private String landed_at;
        private String submitted_at;
        private Hidden hidden;
        private Definition definition;
        private List<Answer> answers;
        private Ending ending;
        
        @Data
        public static class Hidden {
            private String email;
            private String name;
            private String schedule_no;
            private String user_id;
        }
        
        @Data
        public static class Definition {
            private String id;
            private String title;
            private List<Field> fields;
            private List<Ending> endings;
            
            @Data
            public static class Field {
                private String id;
                private String ref;
                private String type;
                private String title;
                private List<Choice> choices;
                
                @Data
                public static class Choice {
                    private String id;
                    private String ref;
                    private String label;
                }
            }

            @Data
            public static class Ending {
                private String id;
                private String ref;
                private String title;
                private String type;
                private Properties properties;

                @Data
                public static class Properties {
                    private String button_text;
                    private boolean show_button;
                    private boolean share_icons;
                    private String button_mode;
                }
            }
        }

        @Data
        public static class Answer {
            private String type;
            private Choice choice;
            private Field field;

            @Data
            public static class Choice {
                private String id;
                private String label;
                private String ref;
            }

            @Data
            public static class Field {
                private String id;
                private String type;
                private String ref;
            }
        }

        @Data
        public static class Ending {
            private String id;
            private String ref;
        }
    }

}
