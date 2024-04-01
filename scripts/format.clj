#!/usr/bin/env bb

(require '[clojure.string :as str])

(def re
  (re-pattern "\\P{M}\\p{M}*+"))

(def formats
  {:square "󰎣󰎦󰎩󰎬󰎮󰎰󰎵󰎸󰎻󰎾"
   :double-square "󰎢󰎥󰎨󰎫󰎲󰎯󰎴󰎷󰎺󰎽"
   :digital "🯰🯱🯲🯳🯴🯵🯶🯷🯸🯹"})

(defn char->long
  [c]
  (parse-long (str c)))

(defn by-format
  [s fmt]
  (let [fmt (or (some-> fmt keyword) :square)
        numbers (->> fmt (get formats) (re-seq re) (vec))]
    (->> s
         (map (comp (partial get numbers) char->long))
         (str/join))))

(let [[s fmt] *command-line-args*]
  (print (by-format s fmt)))
