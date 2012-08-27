(ns sencha.database
  (:require [clojure.java.jdbc :as sql]))

;; This is a simple create-drop table setup
;; You might want to change this before going into production

(def production {:name "java:comp/env/jdbc/ClojureDB"})

(def testing { :classname   "org.h2.Driver"
    :subprotocol "h2:file"
    :subname     "db/contacts"})

;; Choose either the testing or production database
;; Note that the testing database is improper for .war deployment on cloudbees.

(def db testing)

(defn create-tables []
  (sql/with-connection db
    (sql/create-table
      :contacts
      [:id :integer "PRIMARY KEY" "AUTO_INCREMENT"]
      [:contact "varchar(50)" "NOT NULL"]
      [:info "varchar(255)"])))

(defn drop-tables []
  (sql/with-connection db
    (try 
      (sql/drop-table :contacts)
    (catch Exception _
      (println "Couldn't drop table.")))))

(defn -main []
	(drop-tables)
	(create-tables))