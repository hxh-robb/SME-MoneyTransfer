#! /bin/sh
rsync -av --delete --update --exclude=".git" ./ ~/GitLab/B/platform-parent/ft