(ns sencha.database
  (:require [clojure.java.jdbc :as sql]))

;; This is a simple create-drop table setup
;; You might want to change this before going into production

(def db {:name "java:comp/env/jdbc/ClojureDB"})

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