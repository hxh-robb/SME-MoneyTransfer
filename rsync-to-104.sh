#! /bin/sh
#rsync -av --delete --update --exclude=".git" --exclude=".idea" ./ ~/GitLab/B/platform-parent/ft
rsync -vcr --delete --update --exclude=".git" --exclude=".idea" ./ ~/GitLab/B/platform-parent/ft
