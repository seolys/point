import os
import requests
from datetime import datetime

def notify_prs(slack_webhook_url, repo_owner, repo_name):
    response = requests.get(f'https://api.github.com/repos/{repo_owner}/{repo_name}/pulls?state=open&sort=created&direction=asc')
    unreviewed_prs = [pr for pr in response.json() if not pr['requested_reviewers']]

    pr_messages = []
    for pr in unreviewed_prs:
        created_at = datetime.strptime(pr['created_at'], '%Y-%m-%dT%H:%M:%SZ')
        current_date = datetime.utcnow()
        days_since_creation = (current_date - created_at).days

        pr_link = f"<https://github.com/{repo_owner}/{repo_name}/pull/{pr['number']}|{pr['title']}>"

        pr_messages.append(f"*PR 제목:* {pr_link}\n*작성자:* {pr['user']['login']}\n*등록일로부터 경과 일수:* {days_since_creation}일\n---")

    message_text = f"리뷰가 필요한 PR이 {len(unreviewed_prs)}개 있습니다.\n\n{'\n'.join(pr_messages)}"

    send_slack_notification(slack_webhook_url, message_text)

def send_slack_notification(webhook_url, message_text):
    payload = {
        'text': message_text
    }

    print(message_text)

    # response = requests.post(webhook_url, json=payload)

    # if response.status_code == 200:
    #     print("Slack 알림이 성공적으로 전송되었습니다.")
    # else:
    #     print(f"Slack 알림 전송 실패. 상태 코드: {response.status_code}, 응답 내용: {response.text}")

if __name__ == '__main__':
    slack_webhook_url = os.environ.get('SLACK_WEBHOOK_URL')
    repo_owner = os.environ.get('REPOSITORY_OWNER')
    repo_name = os.environ.get('REPOSITORY_NAME')

    notify_prs(slack_webhook_url, repo_owner, repo_name)
