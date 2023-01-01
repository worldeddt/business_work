psql+=( --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" )

        echo
        for f in /docker-entrypoint-initdb.d/*; do
            case "$f" in
                *.sh)     echo "$0: running $f"; . "$f" ;;
                *.sql)    echo "$0: running $f"; "${psql[@]}" < "$f"; echo ;;
                *.sql.gz) echo "$0: running $f"; gunzip -c "$f" | "${psql[@]}"; echo ;;
                *)        echo "$0: ignoring $f" ;;
            esac
            echo
done